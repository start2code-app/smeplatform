package com.gcs.gcsplatform.web.components.invoice;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import org.springframework.stereotype.Component;

@Component(InvoiceBackportBean.NAME)
public class InvoiceBackportBean {

    public static final String NAME = "gcsplatform_InvoiceBackportBean";

    /**
     * Backports changes of specified invoice line to a corresponding trade.
     *
     * @param invoiceLine Invoice line
     */
    public void backportChangesToTrade(InvoiceLine invoiceLine) {
        ClosedTrade trade = invoiceLine.getTrade();
        trade.setBrokerage(invoiceLine.getBrokerage(), invoiceLine.getTradeSide());
        trade.setMaturityDate(invoiceLine.getMaturityDate());
        trade.setValueDate(invoiceLine.getValueDate());
        trade.setNumdays(invoiceLine.getNumdays());
        trade.setNominal(invoiceLine.getNominal());
        trade.setNotes(invoiceLine.getNotes());
        trade.setGbpEquivalent(invoiceLine.getGbpEquivalent(), invoiceLine.getTradeSide());
        trade.setPnl(invoiceLine.getPnl(), invoiceLine.getTradeSide());
    }
}