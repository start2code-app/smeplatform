package com.gcs.gcsplatform.util;

import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;

import static org.apache.commons.lang3.StringUtils.firstNonBlank;

public class ContractNumber {

    private static final String UNKNOWN_CHAR = "X";

    private String category;
    private String brokerageType;
    private String traderef;
    private String side;
    private String broker;

    private ContractNumber(String category, String brokerageType, String traderef, String side, String broker) {
        this.category = category;
        this.brokerageType = brokerageType;
        this.traderef = traderef;
        this.side = side;
        this.broker = broker;
    }

    public static ContractNumber of(Trade trade, TradeSide side) {
        String category = firstNonBlank(trade.getCategory(), UNKNOWN_CHAR);
        String brokerageType = trade.getBrokerageType() != null ? trade.getBrokerageType().getId() : UNKNOWN_CHAR;
        String traderef = firstNonBlank(trade.getTraderef(), UNKNOWN_CHAR);
        String tradeSide = side != null ? side.getId() : UNKNOWN_CHAR;
        String broker = firstNonBlank(trade.getBroker(side), UNKNOWN_CHAR);
        return new ContractNumber(category, brokerageType, traderef, tradeSide, broker);
    }

    public static ContractNumber of(String contractNumber) {
        String[] strings = contractNumber.split(":");
        if (strings.length == 3) {
            return new ContractNumber(UNKNOWN_CHAR, UNKNOWN_CHAR, strings[0], strings[1], strings[2]);
        } else if (strings.length == 5) {
            return new ContractNumber(strings[0], strings[1], strings[2], strings[3], strings[4]);
        } else {
            throw new IllegalArgumentException(
                    "Contract number can only have one of these formats: 'X:X:X:X:X' or 'X:X:X'");
        }
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s:%s:%s", category, brokerageType, traderef, side, broker);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? UNKNOWN_CHAR : category;
    }

    public String getBrokerageType() {
        return brokerageType;
    }

    public void setBrokerageType(BrokerageType brokerageType) {
        this.brokerageType = brokerageType == null ? UNKNOWN_CHAR : brokerageType.getId();
    }

    public String getTraderef() {
        return traderef;
    }

    public void setTraderef(String traderef) {
        this.traderef = traderef == null ? UNKNOWN_CHAR : traderef;
    }

    public String getSide() {
        return side;
    }

    public void setSide(TradeSide side) {
        this.side = side == null ? UNKNOWN_CHAR : side.getId();
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker == null ? UNKNOWN_CHAR : broker;
    }
}
