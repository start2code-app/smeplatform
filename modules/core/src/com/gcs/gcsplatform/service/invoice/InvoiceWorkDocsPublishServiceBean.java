package com.gcs.gcsplatform.service.invoice;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.core.workdocs.AmazonWorkDocsUploader;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;
import com.gcs.gcsplatform.service.CompanyService;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_FILE_NOT_FOUND;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_COMPANY_NOT_FOUND;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.INVOICE_NOT_PRINTED;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.WORKDOCS_FOLDER_NOT_CONFIGURED;
import static com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType.WORKDOCS_UPLOAD_FAILED;
import static com.gcs.gcsplatform.util.ContentType.CONTENT_TYPE_EXCEL;
import static com.gcs.gcsplatform.util.ContentType.CONTENT_TYPE_PDF;

@Service(InvoiceWorkDocsPublishService.NAME)
public class InvoiceWorkDocsPublishServiceBean implements InvoiceWorkDocsPublishService {

    @Inject
    private DataManager dataManager;
    @Inject
    private AmazonWorkDocsUploader amazonWorkDocsUploader;
    @Inject
    private FileStorageAPI fileStorageAPI;
    @Inject
    private CompanyService companyService;
    @Inject
    private Logger log;

    @Nullable
    @Override
    public InvoicePublishError publishToWorkDocs(Invoice invoice) {
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

        String folderId = invoiceCompany.getWorkDocsFolderId();
        if (StringUtils.isBlank(folderId)) {
            return InvoicePublishError.of(invoice, WORKDOCS_FOLDER_NOT_CONFIGURED);
        }

        try {
            uploadFile(invoice.getXlsxFile(), folderId, CONTENT_TYPE_EXCEL);
            uploadFile(invoice.getPdfFile(), folderId, CONTENT_TYPE_PDF);
        } catch (FileStorageException e) {
            log.error("File is not found", e);
            return InvoicePublishError.of(invoice, INVOICE_FILE_NOT_FOUND);
        } catch (Exception e) {
            log.error("WorkDocs upload failed", e);
            return InvoicePublishError.of(invoice, WORKDOCS_UPLOAD_FAILED);
        }

        invoice.setPostedToWorkDocs(true);
        dataManager.commit(invoice);
        return null;
    }

    private void uploadFile(FileDescriptor fileDescriptor, String folderId, String contentType)
            throws FileStorageException {
        byte[] content = fileStorageAPI.loadFile(fileDescriptor);
        amazonWorkDocsUploader.uploadFile(fileDescriptor.getName(), folderId, content, contentType);
    }
}