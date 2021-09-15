package com.gcs.gcsplatform.web.components.trade;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.util.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component(TradeValidationBean.NAME)
public class TradeValidationBean {

    public static final String NAME = "gcsplatform_TradeValidationBean";

    /**
     * Validates trade required fields.
     * Maturity date is assumed to be non-required for Open trades.
     *
     * @param trade Trade
     * @return true if any of required field is null or empty.
     */
    public boolean hasBlankRequiredFields(Trade trade) {
        boolean isSplitBrokerBlank = (Boolean.TRUE.equals(trade.getBuySplit()) && StringUtils.isBlank(
                trade.getBuySplitBroker()))
                || (Boolean.TRUE.equals(trade.getSellSplit()) && StringUtils.isBlank(trade.getSellSplitBroker()));

        boolean isBrokerageTypeBlank = trade.getBrokerageType() == null;

        boolean isBrokerageBlank = Boolean.TRUE.equals(trade.getBrooveride()) && (trade.getBuybrokerage() == null
                || trade.getSellbrokerage() == null);

        boolean isOrigtraderefBlank = Boolean.TRUE.equals(trade.getSubs()) && StringUtils.isBlank(
                trade.getOrigtraderef());

        boolean isTextFieldsBlank = StringUtils.isAnyBlank(trade.getBuybroker(),
                trade.getSellbroker(),
                trade.getTraderef(),
                trade.getIsin(),
                trade.getCurrency(),
                trade.getTradeCurrency(),
                trade.getBuyer(),
                trade.getSeller(),
                trade.getBuyerLocation(),
                trade.getSellerLocation(),
                trade.getBuyerAgent(),
                trade.getSellerAgent(),
                trade.getCategory());

        boolean isNumbersBlank = trade.getNominal() == null
                || trade.getRepoRate() == null
                || trade.getStartPrice() == null
                || trade.getHairCut() == null;

        boolean isDatesBlank = trade.getValueDate() == null
                || trade.getTradeDate() == null
                || trade.getInvoiceDate() == null
                || !(trade instanceof OpenedTrade) && trade.getMaturityDate() == null;

        return isSplitBrokerBlank
                || isBrokerageTypeBlank
                || isBrokerageBlank
                || isOrigtraderefBlank
                || isTextFieldsBlank
                || isNumbersBlank
                || isDatesBlank;
    }

    /**
     * Validates trade for brokerage.
     *
     * @param trade Trade
     * @return True if sub is unchecked and buy brokerage or sell brokerage is zero or null.
     */
    public boolean hasZeroBrokerage(Trade trade) {
        return !Boolean.TRUE.equals(trade.getSubs()) && BigDecimalUtils.isAnyNullOrZero(trade.getBuybrokerage(),
                trade.getSellbrokerage());
    }
}
