package com.gcs.gcsplatform.service.invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.service.fx.FxCalculationService;
import com.gcs.gcsplatform.service.fx.FxService;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.getNumberOrNull;

@Service(InvoiceService.NAME)
public class InvoiceServiceBean implements InvoiceService {

    @Inject
    private DataManager dataManager;
    @Inject
    private FxService fxService;
    @Inject
    private FxCalculationService fxCalculationService;

    @Override
    public Collection<Invoice> createInvoices(Collection<InvoiceLine> invoiceLines) {
        Map<InvoiceGroup, Invoice> invoiceMap = new HashMap<>();
        for (InvoiceLine invoiceLine : invoiceLines) {
            groupInvoiceByCriteria(invoiceMap, invoiceLine);
        }
        return new ArrayList<>(invoiceMap.values());
    }

    private void groupInvoiceByCriteria(Map<InvoiceGroup, Invoice> invoiceMap, InvoiceLine invoiceLine) {
        InvoiceGroup invoiceGroup = new InvoiceGroup(invoiceLine.getCurrency(), invoiceLine.getStartDate(),
                invoiceLine.getLocation(), invoiceLine.getCounterpartyCode());
        if (!invoiceMap.containsKey(invoiceGroup)) {
            Invoice invoice = createInvoice(invoiceLine);
            invoiceMap.put(invoiceGroup, invoice);
        } else {
            Invoice invoice = invoiceMap.get(invoiceGroup);
            invoice.setAmount(invoice.getAmount().add(invoiceLine.getPnl()));
            invoice.setGbpAmount(invoice.getGbpAmount().add(invoiceLine.getGbpEquivalent()));
        }
    }

    private Invoice createInvoice(InvoiceLine invoiceLine) {
        Invoice invoice = dataManager.create(Invoice.class);
        invoice.setLocation(invoiceLine.getLocation());
        invoice.setCurrency(invoiceLine.getCurrency());
        invoice.setCounterpartyCode(invoiceLine.getCounterpartyCode());
        invoice.setStartDate(invoiceLine.getStartDate());
        invoice.setEndDate(invoiceLine.getEndDate());
        invoice.setFxUsd(fxService.findUsdFxValue(invoiceLine.getStartDate()));
        invoice.setFx(invoiceLine.getFx());
        invoice.setAmount(invoiceLine.getPnl());
        invoice.setGbpAmount(invoiceLine.getGbpEquivalent());
        invoice.setUsdAmount(fxCalculationService.calculateUsdEquivalent(invoice.getGbpAmount(), invoice.getFxUsd()));
        return invoice;
    }

    @Override
    public Invoice calculateAmount(Invoice invoice) {
        KeyValueEntity keyValue = dataManager.loadValues(
                "select sum(e.pnl) as amount, sum(e.gbpEquivalent) as gbpAmount "
                        + "from gcsplatform_InvoiceLine e "
                        + "where e.startDate = :startDate "
                        + "and e.currency = :currency "
                        + "and e.counterpartyCode = :counterpartyCode "
                        + "and e.location = :location")
                .parameter("startDate", invoice.getStartDate())
                .parameter("currency", invoice.getCurrency())
                .parameter("counterpartyCode", invoice.getCounterpartyCode())
                .parameter("location", invoice.getLocation())
                .properties("amount", "gbpAmount")
                .one();
        invoice.setAmount(getNumberOrNull(keyValue.getValue("amount")));
        invoice.setGbpAmount(getNumberOrNull(keyValue.getValue("gbpAmount")));
        invoice.setUsdAmount(fxCalculationService.calculateUsdEquivalent(invoice.getGbpAmount(), invoice.getFxUsd()));
        if (Boolean.TRUE.equals(invoice.getPrinted())) {
            invoice.setIssue(invoice.getIssue() + 1);
            invoice.setPrinted(false);
            invoice.setPosted(false);
        }
        return invoice;
    }

    @Nullable
    @Override
    public Invoice findInvoice(InvoiceLine invoiceLine, View view) {
        return dataManager.load(Invoice.class)
                .query("select e from gcsplatform_Invoice e "
                        + "where e.startDate = :startDate "
                        + "and e.currency = :currency "
                        + "and e.counterpartyCode = :counterpartyCode "
                        + "and e.location = :location")
                .parameter("startDate", invoiceLine.getStartDate())
                .parameter("currency", invoiceLine.getCurrency())
                .parameter("counterpartyCode", invoiceLine.getCounterpartyCode())
                .parameter("location", invoiceLine.getLocation())
                .view(view)
                .optional()
                .orElse(null);
    }

    private static class InvoiceGroup {

        private final String currency;
        private final Date startDate;
        private final String location;
        private final String counterpartyCode;

        public InvoiceGroup(String currency, Date startDate, String location, String counterpartyCode) {
            this.currency = currency;
            this.startDate = startDate;
            this.location = location;
            this.counterpartyCode = counterpartyCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InvoiceGroup that = (InvoiceGroup) o;
            return Objects.equals(currency, that.currency) && Objects.equals(startDate, that.startDate)
                    && Objects.equals(location, that.location) && Objects.equals(counterpartyCode,
                    that.counterpartyCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(currency, startDate, location, counterpartyCode);
        }
    }
}