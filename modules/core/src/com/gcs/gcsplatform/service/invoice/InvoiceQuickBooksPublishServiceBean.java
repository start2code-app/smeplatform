package com.gcs.gcsplatform.service.invoice;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.gcs.gcsplatform.config.LocationConfig;
import com.gcs.gcsplatform.config.QuickBooksConfig;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;
import com.gcs.gcsplatform.exception.QuickBooksException;
import com.gcs.gcsplatform.exception.QuickBooksTokenRefreshException;
import com.gcs.gcsplatform.service.CompanyService;
import com.gcs.gcsplatform.service.qb.QuickBooksConnectorService;
import com.gcs.gcsplatform.util.ContentType;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableRef;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.ExchangeRate;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_FILE_NOT_FOUND;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_COMPANY_NOT_FOUND;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_NOT_PRINTED;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.QUICKBOOKS_PUBLISH_FAILED;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.QUICKBOOKS_REALM_NOT_CONFIGURED;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.QUICKBOOKS_TOKEN_REFRESH_FAILED;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfPreviousMonth;

@Service(InvoiceQuickBooksPublishService.NAME)
public class InvoiceQuickBooksPublishServiceBean implements InvoiceQuickBooksPublishService {

    private static final String BROKERAGE_ITEM_NAME = "Brokerage";
    private static final String TAX_CODE_NAME = "Exempt";
    private static final String TERM_NAME = "30 days";

    @Inject
    private DataManager dataManager;
    @Inject
    private QuickBooksConnectorService quickBooksConnectorService;
    @Inject
    private FileStorageAPI fileStorageAPI;
    @Inject
    private QuickBooksConfig quickBooksConfig;
    @Inject
    private CompanyService companyService;
    @Inject
    private LocationConfig locationConfig;
    @Inject
    private CurrencyConfig currencyConfig;
    @Inject
    private Logger log;

    @Override
    public InvoicePublishError publishToQB(Invoice invoice) {
        if (!invoice.getPrinted()) {
            return InvoicePublishError.of(invoice, INVOICE_NOT_PRINTED);
        }

        InvoiceCompany invoiceCompany = companyService.findInvoiceCompany(invoice.getLocation(),
                ViewBuilder.of(InvoiceCompany.class)
                        .addView(View.LOCAL)
                        .build());
        if (invoiceCompany == null) {
            return InvoicePublishError.of(invoice, INVOICE_COMPANY_NOT_FOUND);
        }

        String realmId = invoiceCompany.getQbRealmId();
        if (StringUtils.isBlank(realmId)) {
            return InvoicePublishError.of(invoice, QUICKBOOKS_REALM_NOT_CONFIGURED);
        }

        try {
            DataService ds = quickBooksConnectorService.getDataService(realmId);
            publish(invoice, ds);
        } catch (QuickBooksTokenRefreshException e) {
            log.warn("QuickBooks needs reauthorize", e);
            return InvoicePublishError.of(invoice, QUICKBOOKS_TOKEN_REFRESH_FAILED);
        } catch (FileStorageException e) {
            log.error("File is not found", e);
            return InvoicePublishError.of(invoice, INVOICE_FILE_NOT_FOUND);
        } catch (Exception e) {
            log.error("QuickBooks upload failed", e);
            return InvoicePublishError.of(invoice, QUICKBOOKS_PUBLISH_FAILED);
        }

        invoice.setPostedToQB(true);
        dataManager.commit(invoice);
        return null;
    }

    private void publish(Invoice invoice, DataService ds) throws FMSException, FileStorageException {
        com.intuit.ipp.data.Invoice qbInvoice = new com.intuit.ipp.data.Invoice();
        qbInvoice.setTxnDate(invoice.getEndDate());
        qbInvoice.setDocNumber(invoice.getInvoiceNumber());
        qbInvoice.setLine(getLines(ds, invoice));
        qbInvoice.setCustomerRef(getCustomer(ds, invoice));
        qbInvoice.setSalesTermRef(getTerm(ds));
        qbInvoice.setExchangeRate(getExchangeRate(ds, invoice));

        deleteOldInvoice(ds, invoice.getInvoiceNumberWithoutVersion());

        com.intuit.ipp.data.Invoice savedInvoice = ds.add(qbInvoice);
        uploadAttachment(ds, invoice.getPdfFile(), ContentType.CONTENT_TYPE_PDF, savedInvoice);
        if (!quickBooksConfig.getSkipExcelCounterpartyCodes().contains(invoice.getCounterpartyCode())) {
            uploadAttachment(ds, invoice.getXlsxFile(), ContentType.CONTENT_TYPE_EXCEL, savedInvoice);
        }
    }


    private List<Line> getLines(DataService ds, Invoice invoice) throws FMSException {
        SalesItemLineDetail silDetails = new SalesItemLineDetail();
        silDetails.setItemRef(getBrokerage(ds));
        if (locationConfig.getLondonLocation().getName().equals(invoice.getLocation())) {
            silDetails.setTaxCodeRef(getTaxCode(ds));
        }

        Line line = new Line();
        line.setDescription("Brokerage - " + invoice.getCounterpartyCode());
        line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
        line.setSalesItemLineDetail(silDetails);
        if (Boolean.TRUE.equals(invoice.getShowTotalUsd())) {
            line.setAmount(invoice.getUsdAmount());
        } else {
            line.setAmount(invoice.getAmount());
        }

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        return lines;
    }

    private ReferenceType getBrokerage(DataService ds) throws FMSException {
        QueryResult result = ds.executeQuery(
                String.format("select * from item where name = '%s'", BROKERAGE_ITEM_NAME));
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("Item with name '" + BROKERAGE_ITEM_NAME + "' was not found");
        }
        Item item = (Item) entities.get(0);
        ReferenceType itemRef = new ReferenceType();
        itemRef.setName(item.getName());
        itemRef.setValue(item.getId());
        return itemRef;
    }

    private ReferenceType getTaxCode(DataService ds) throws FMSException {
        QueryResult result = ds.executeQuery(String.format("select * from taxcode where name = '%s'", TAX_CODE_NAME));
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("TaxCode with name '" + TAX_CODE_NAME + "' was not found");
        }
        TaxCode tc = (TaxCode) entities.get(0);
        ReferenceType taxCodeRef = new ReferenceType();
        taxCodeRef.setName(tc.getName());
        taxCodeRef.setValue(tc.getId());
        return taxCodeRef;
    }

    private ReferenceType getCustomer(DataService ds, Invoice invoice) throws FMSException {
        String customerName = invoice.getCounterpartyCode() + " " + invoice.getCurrency();
        QueryResult result = ds.executeQuery(
                String.format("select * from customer where displayname = '%s'", customerName));
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("Customer with name '" + customerName + "' was not found");
        }
        Customer customer = (Customer) entities.get(0);
        ReferenceType customerRef = new ReferenceType();
        customerRef.setName(customer.getDisplayName());
        customerRef.setValue(customer.getId());
        return customerRef;
    }

    private ReferenceType getTerm(DataService ds) throws FMSException {
        QueryResult result = ds.executeQuery(String.format("select * from term where name = '%s'", TERM_NAME));
        Term term;
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("Term with name '" + TERM_NAME + "' was not found");
        }
        term = (Term) entities.get(0);
        ReferenceType termRef = new ReferenceType();
        termRef.setName(term.getName());
        termRef.setValue(term.getId());
        return termRef;
    }

    private BigDecimal getExchangeRate(DataService ds, Invoice invoice) throws FMSException {
        String asofdate = new SimpleDateFormat("yyyy-MM-dd").format(getLastDayOfPreviousMonth());
        String currency;
        if (Boolean.TRUE.equals(invoice.getShowTotalUsd())) {
            currency = currencyConfig.getUsdCurrency().getCurrency();
        } else {
            currency = invoice.getCurrency();
        }

        QueryResult result = ds.executeQuery(String.format("select * from exchangerate "
                + "where sourcecurrencycode='%s' and asofdate= '%s'", currency, asofdate));
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("Exchange rate for currency '" + currency + "' was not found");
        }
        ExchangeRate er = (ExchangeRate) entities.get(0);
        return er.getRate();
    }

    private void deleteOldInvoice(DataService ds, String invoiceNumberWithoutVersion) throws FMSException {
        QueryResult result = ds.executeQuery(
                String.format("select * from Invoice where docNumber like '%s%%'", invoiceNumberWithoutVersion));
        List<? extends IEntity> entities = result.getEntities();
        if (!entities.isEmpty()) {
            com.intuit.ipp.data.Invoice oldInvoice = (com.intuit.ipp.data.Invoice) entities.get(0);
            ds.delete(oldInvoice);
        }
    }

    private void uploadAttachment(DataService ds, FileDescriptor fileDescriptor, String contentType,
            com.intuit.ipp.data.Invoice savedInvoice) throws FileStorageException, FMSException {
        Attachable attachable = new Attachable();
        attachable.setLat("25.293112341223");
        attachable.setLong("-21.3253249834");
        attachable.setPlaceName("Fake Place");
        attachable.setNote("Attachable note " + savedInvoice.getDocNumber());
        attachable.setTag("Attachable tag " + savedInvoice.getDocNumber());
        attachable.setFileName(fileDescriptor.getName());
        attachable.setContentType(contentType);

        ReferenceType invoiceRef = new ReferenceType();
        invoiceRef.setName(savedInvoice.getDocNumber());
        invoiceRef.setValue(savedInvoice.getId());
        invoiceRef.setType("Invoice");
        invoiceRef.setValue(savedInvoice.getId());

        AttachableRef attachableRef = new AttachableRef();
        attachableRef.setEntityRef(invoiceRef);
        attachableRef.setIncludeOnSend(true);
        List<AttachableRef> listAttachRef = new ArrayList<>();
        listAttachRef.add(attachableRef);
        attachable.setAttachableRef(listAttachRef);
        InputStream inputStream = fileStorageAPI.openStream(fileDescriptor);
        ds.upload(attachable, inputStream);
    }
}