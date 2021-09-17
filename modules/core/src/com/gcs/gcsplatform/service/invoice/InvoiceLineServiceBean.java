package com.gcs.gcsplatform.service.invoice;

import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;

@Service(InvoiceLineService.NAME)
public class InvoiceLineServiceBean implements InvoiceLineService {

    @Inject
    private Metadata metadata;

    @Override
    public Collection<InvoiceLine> splitTrade(ClosedTrade trade) {
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(splitTrade(trade, TradeSide.BUY));
        invoiceLines.add(splitTrade(trade, TradeSide.SELL));
        return invoiceLines;
    }

    private InvoiceLine splitTrade(ClosedTrade trade, TradeSide side) {
        InvoiceLine invoiceLine = metadata.create(InvoiceLine.class);
        invoiceLine.setTrade(trade);
        invoiceLine.setBroker(trade.getBroker(side));
        invoiceLine.setCrossRate(trade.getCpair1());
        invoiceLine.setHairCut(trade.getHairCut());
        invoiceLine.setCurrency(trade.getTradeCurrency());
        invoiceLine.setBaseCurrency(trade.getCurrency());
        invoiceLine.setXrate(trade.getXrate2());
        invoiceLine.setValueDate(trade.getValueDate());
        invoiceLine.setMaturityDate(trade.getMaturityDate());
        invoiceLine.setLocation(trade.getInvoiceCode(side));
        invoiceLine.setNominal(trade.getNominal());
        invoiceLine.setTradeDate(trade.getTradeDate());
        invoiceLine.setCounterpartyCode(trade.getCounterpartyCode(side));
        invoiceLine.setRepoRate(trade.getRepoRate());
        invoiceLine.setNotes(trade.getNotes());
        invoiceLine.setBuyer(trade.getBuyer());
        invoiceLine.setSeller(trade.getSeller());
        invoiceLine.setCounterparty(trade.getCounterparty(side));
        invoiceLine.setNumdays(trade.getNumdays());
        invoiceLine.setStartDate(getFirstDayOfMonth(trade.getInvoiceDate()));
        invoiceLine.setEndDate(getLastDayOfMonth(trade.getInvoiceDate()));
        invoiceLine.setIsin(trade.getIsin());
        invoiceLine.setCash(trade.getCash(side));
        invoiceLine.setBondDescription(trade.getBondDescription());
        invoiceLine.setBrokerage(trade.getBrokerage(side));
        invoiceLine.setContractNumber(generateContractNumber(trade, side));
        invoiceLine.setStartPrice(trade.getStartPrice());
        invoiceLine.setPnl(trade.getPnl(side));
        invoiceLine.setFx(trade.getXrate1());
        invoiceLine.setTradeSide(side);
        invoiceLine.setGbpEquivalent(trade.getGbpEquivalent(side));
        return invoiceLine;
    }

    private String generateContractNumber(Trade trade, TradeSide side) {
        String category = firstNonBlank(trade.getCategory(), "X");
        String brokerageType = trade.getBrokerageType() != null ? trade.getBrokerageType().getId() : "X";
        String traderef = firstNonBlank(trade.getTraderef(), "X");
        String broker = firstNonBlank(trade.getBroker(side), "X");
        return String.format("%s:%s:%s:%s:%s", category, brokerageType, traderef, side.getId(), broker);
    }
}