package com.gcs.gcsplatform.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.util.DateUtils;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service(InvoiceService.NAME)
public class InvoiceServiceBean implements InvoiceService {

    @Inject
    private Metadata metadata;

    @Override
    public Collection<Invoice> createInvoices(Collection<? extends Trade> trades) {
        if (CollectionUtils.isEmpty(trades)) {
            return Collections.emptyList();
        }
        Map<InvoiceGroup, Invoice> invoiceMap = new HashMap<>();
        for (Trade trade : trades) {
            addInvoice(invoiceMap, trade, TradeSide.BUY);
            addInvoice(invoiceMap, trade, TradeSide.SELL);
        }
        return new ArrayList<>(invoiceMap.values());
    }

    private void addInvoice(Map<InvoiceGroup, Invoice> invoiceMap, Trade trade, TradeSide side) {
        InvoiceGroup invoiceGroup = new InvoiceGroup(DateUtils.getFirstDayOfMonth(trade.getInvoiceDate()),
                trade.getTradeCurrency(), trade.getCounterpartyCode(side));
        InvoiceLine invoiceLine = createInvoiceLine(trade, side);
        if (invoiceMap.containsKey(invoiceGroup)) {
            List<InvoiceLine> lines = invoiceMap.get(invoiceGroup).getLines();
            lines.add(invoiceLine);
        } else {
            List<InvoiceLine> lines = new ArrayList<>();
            lines.add(invoiceLine);
            Invoice invoice = createInvoice(invoiceGroup.getStartDate(), invoiceGroup.getCurrency(),
                    invoiceGroup.getCounterpartyCode(), lines);
            invoiceMap.put(invoiceGroup, invoice);
        }
    }

    private InvoiceLine createInvoiceLine(Trade trade, TradeSide side) {
        InvoiceLine invoiceLine = metadata.create(InvoiceLine.class);
        invoiceLine.setBrokerage(trade.getBrokerage(side));
        invoiceLine.setInvoiceCode(trade.getInvoiceCode(side));
        invoiceLine.setBaseCurrency(trade.getCurrency()); // todo: ??? is it ???
        invoiceLine.setBuyer(trade.getBuyer());
        invoiceLine.setSeller(trade.getSeller());
        invoiceLine.setBondDescription(trade.getBondDescription());
        invoiceLine.setCash(trade.getCash(side));
        invoiceLine.setContractNumber(trade.getTraderef()); // todo: generate contract num X:X:tradeRef:X:X
        invoiceLine.setIsin(trade.getIsin());
        invoiceLine.setMaturityDate(trade.getMaturityDate());
        invoiceLine.setStartPrice(trade.getStartPrice());
        invoiceLine.setNominal(trade.getNominal());
        invoiceLine.setTradeDate(trade.getTradeDate());
        invoiceLine.setValueDate(trade.getValueDate());
        invoiceLine.setXrate(trade.getXrate2());
        //invoiceLine.setPnl(); // todo: calculate pnl
        //invoiceLine.setGbpEquivalent(); // todo: calculate gbp
        return invoiceLine;
    }

    private Invoice createInvoice(Date startDate, String currency, String counterpartyCode, List<InvoiceLine> lines) {
        Invoice invoice = metadata.create(Invoice.class);
        invoice.setStartDate(startDate);
        invoice.setCurrency(currency);
        invoice.setCounterpartyCode(counterpartyCode);
        invoice.setLines(lines);
        //invoice.setFx();
        //invoice.setFxUsd();
        //invoice.setAmount(); // todo: set after all invoice lines created
        //invoice.setGbpAmount();
        return invoice;
    }

    private static class InvoiceGroup {

        private final Date startDate;
        private final String currency;
        private final String counterpartyCode;

        public InvoiceGroup(Date startDate, String currency, String counterpartyCode) {
            this.startDate = startDate;
            this.currency = currency;
            this.counterpartyCode = counterpartyCode;
        }

        public Date getStartDate() {
            return startDate;
        }

        public String getCurrency() {
            return currency;
        }

        public String getCounterpartyCode() {
            return counterpartyCode;
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
            return Objects.equals(startDate, that.startDate) && Objects.equals(currency, that.currency)
                    && Objects.equals(counterpartyCode, that.counterpartyCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, currency, counterpartyCode);
        }
    }
}