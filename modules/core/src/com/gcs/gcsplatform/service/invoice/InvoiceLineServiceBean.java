package com.gcs.gcsplatform.service.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.FxService;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType.GC;
import static com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType.SPECIAL;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;

@Service(InvoiceLineService.NAME)
public class InvoiceLineServiceBean implements InvoiceLineService {

    private Map<CounterpartyBrokerageType, String> brokerageTypeForInvoiceMap;

    @Inject
    private DataManager dataManager;
    @Inject
    private PnlCalculationService pnlCalculationService;
    @Inject
    private FxService fxService;

    @PostConstruct
    private void init() {
        brokerageTypeForInvoiceMap = new HashMap<>();
        brokerageTypeForInvoiceMap.put(GC, "GEN");
        brokerageTypeForInvoiceMap.put(SPECIAL, "SPE");
    }

    @Override
    public Collection<InvoiceLine> splitTrade(ClosedTrade trade) {
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
        BigDecimal fxValue = fxService.getFxValue(trade.getTradeCurrency(), trade.getInvoiceDate());
        BigDecimal fxUsdValue = fxService.getUsdFxValue(trade.getInvoiceDate());
        invoiceLines.add(splitTrade(trade, TradeSide.BUY, fxValue, fxUsdValue));
        invoiceLines.add(splitTrade(trade, TradeSide.SELL, fxValue, fxUsdValue));
        return invoiceLines;
    }

    private InvoiceLine splitTrade(ClosedTrade trade, TradeSide side, BigDecimal fxValue, BigDecimal fxUsdValue) {
        InvoiceLine invoiceLine = dataManager.create(InvoiceLine.class);
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
        BigDecimal pnl = pnlCalculationService.calculatePnl(invoiceLine);
        invoiceLine.setPnl(pnl);
        invoiceLine.setFx(fxValue);
        invoiceLine.setFxUsd(fxUsdValue);
        invoiceLine.setTradeSide(side);
        invoiceLine.setGbpEquivalent(pnlCalculationService.calculateFxEquivalent(pnl, fxValue));
        return invoiceLine;
    }

    private String generateContractNumber(Trade trade, TradeSide side) {
        String category = firstNonBlank(trade.getCategory(), "X");
        String brokerageType = brokerageTypeForInvoiceMap.getOrDefault(trade.getBrokerageType(), "X");
        String traderef = firstNonBlank(trade.getTraderef(), "X");
        String broker = firstNonBlank(trade.getBroker(side), "X");
        return String.format("%s:%s:%s:%s:%s", category, brokerageType, traderef, side.getId(), broker);
    }
}