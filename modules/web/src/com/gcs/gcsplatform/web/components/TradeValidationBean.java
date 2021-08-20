package com.gcs.gcsplatform.web.components;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
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
     * @param tradeContainer - Trade container
     * @return true if any of required field is null or empty.
     */
    public boolean hasBlankRequiredFields(TradeContainer tradeContainer) {
        Trade trade = tradeContainer.getTrade();

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
                || !(tradeContainer instanceof OpenedTrade) && trade.getMaturityDate() == null;

        return isSplitBrokerBlank
                || isBrokerageTypeBlank
                || isBrokerageBlank
                || isOrigtraderefBlank
                || isTextFieldsBlank
                || isNumbersBlank
                || isDatesBlank;
    }

    /**
     * Validates trade for PNL.
     * PNL is always zero in Open trades, therefore no need to validate them.
     *
     * @param tradeContainer - Trade container
     * @return True if sub is unchecked and PNL or GBP equivalent are not calculated (both for buy/sell sides)
     */
    public boolean hasZeroPnl(TradeContainer tradeContainer) {
        if (!(tradeContainer instanceof OpenedTrade)) {
            Trade trade = tradeContainer.getTrade();
            return !Boolean.TRUE.equals(trade.getSubs()) && BigDecimalUtils.isAnyNullOrZero(trade.getBuyPnl(),
                    trade.getSellPnl(),
                    trade.getBuyGbpEquivalent(),
                    trade.getSellGbpEquivalent());
        }
        return false;
    }
}
