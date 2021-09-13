package com.gcs.gcsplatform.web.components.invoice;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import org.springframework.stereotype.Component;

@Component(InvoiceBackportBean.NAME)
public class InvoiceBackportBean {

    public static final String NAME = "gcsplatform_InvoiceBackportBean";

    /**
     * Backports changes of invoice to a corresponding trade.
     *
     * @param invoiceLine Invoice line
     */
    public void backportChangesToTrade(InvoiceLine invoiceLine) {
        ClosedTrade trade = invoiceLine.getTrade();
        trade.setBrokerage(invoiceLine.getBrokerage(), invoiceLine.getTradeSide());
        trade.setCounterparty(invoiceLine.getCounterparty(), invoiceLine.getTradeSide());
        trade.setInvoiceCode(invoiceLine.getLocation(), invoiceLine.getTradeSide());
        trade.setCounterpartyCode(invoiceLine.getCounterpartyCode(), invoiceLine.getTradeSide());
        trade.setTradeCurrency(invoiceLine.getCurrency());
        trade.setMaturityDate(invoiceLine.getMaturityDate());
        trade.setValueDate(invoiceLine.getValueDate());
        trade.setNominal(invoiceLine.getNominal());
        trade.setNotes(invoiceLine.getNotes());
    }
}