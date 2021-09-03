package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType.GC;
import static com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType.SPECIAL;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;
import static org.apache.commons.lang3.StringUtils.defaultString;

@Service(InvoiceService.NAME)
public class InvoiceServiceBean implements InvoiceService {

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
    public Invoice createInvoice(InvoiceLine invoiceLine) {
        Invoice invoice = dataManager.create(Invoice.class);
        invoice.setLocation(invoiceLine.getLocation());
        invoice.setCurrency(invoiceLine.getCurrency());
        invoice.setCounterpartyCode(invoiceLine.getCounterpartyCode());
        Date startDate = invoiceLine.getStartDate();
        invoice.setStartDate(startDate);
        invoice.setFxUsd(fxService.getUsdFxValue(startDate));
        invoice.setFx(invoiceLine.getFx());
        calculateAmount(invoice);
        return invoice;
    }

    private void calculateAmount(Invoice invoice) {
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
        invoice.setAmount(keyValue.getValue("amount"));
        invoice.setGbpAmount(keyValue.getValue("gbpAmount"));
    }

    @Override
    public Collection<InvoiceLine> splitTrade(ClosedTrade trade) {
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
        BigDecimal fxValue = fxService.getFxValue(trade.getTradeCurrency(), trade.getInvoiceDate());
        invoiceLines.add(splitTrade(trade, TradeSide.BUY, fxValue));
        invoiceLines.add(splitTrade(trade, TradeSide.SELL, fxValue));
        return invoiceLines;
    }

    private InvoiceLine splitTrade(ClosedTrade trade, TradeSide side, BigDecimal fxValue) {
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
        BigDecimal pnl = pnlCalculationService.calculatePnl(invoiceLine);
        invoiceLine.setPnl(pnl);
        invoiceLine.setFx(fxValue);
        invoiceLine.setGbpEquivalent(pnlCalculationService.calculateFxEquivalent(pnl, fxValue));
        return invoiceLine;
    }

    private String generateContractNumber(Trade trade, TradeSide side) {
        String category = defaultString(trade.getCategory(), "X");
        String brokerageType = brokerageTypeForInvoiceMap.getOrDefault(trade.getBrokerageType(), "X");
        String traderef = defaultString(trade.getTraderef(), "X");
        String broker = defaultString(trade.getBroker(side), "X");
        return String.format("%s:%s:%s:%s:%s", category, brokerageType, traderef, side, broker);
    }
}