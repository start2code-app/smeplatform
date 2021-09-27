package com.gcs.gcsplatform.web.components.invoice;

import java.math.BigDecimal;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.util.ContractNumber;
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
        /* Legacy data does not contain trade */
        if (trade == null) {
            return;
        }
        trade.setBrokerage(invoiceLine.getBrokerage(), invoiceLine.getTradeSide());
        trade.setMaturityDate(invoiceLine.getMaturityDate());
        trade.setValueDate(invoiceLine.getValueDate());
        trade.setNumdays(invoiceLine.getNumdays());
        trade.setNominal(invoiceLine.getNominal());
        trade.setNotes(invoiceLine.getNotes());
        trade.setGbpEquivalent(invoiceLine.getGbpEquivalent(), invoiceLine.getTradeSide());
        trade.setPnl(invoiceLine.getPnl(), invoiceLine.getTradeSide());
    }

    /**
     * Backports brokerage fields of specified invoice line to a corresponding trade.
     * <p>
     * Re-generates contract number for specified invoice line.
     *
     * @param invoiceLine Invoice line
     * @param category    Category
     * @param type        Brokerage type
     * @param broOverride Brokerage override
     */
    public void backportBrokerage(InvoiceLine invoiceLine, BigDecimal brokerage, String category, BrokerageType type,
            boolean broOverride) {
        ContractNumber contractNumber = ContractNumber.of(invoiceLine.getContractNumber());
        contractNumber.setBrokerageType(type);
        contractNumber.setCategory(category);
        invoiceLine.setContractNumber(contractNumber.toString());
        invoiceLine.setBrokerage(brokerage);
        ClosedTrade trade = invoiceLine.getTrade();
        /* Legacy data does not contain trade */
        if (trade == null) {
            return;
        }
        trade.setCategory(category);
        trade.setBrokerageType(type);
        trade.setBrooveride(broOverride);
    }
}