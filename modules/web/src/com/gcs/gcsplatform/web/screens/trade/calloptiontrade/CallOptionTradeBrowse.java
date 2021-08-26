package com.gcs.gcsplatform.web.screens.trade.calloptiontrade;

import com.gcs.gcsplatform.entity.trade.CallOptionTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_CallOptionTrade.browse")
@UiDescriptor("call-option-trade-browse.xml")
public class CallOptionTradeBrowse extends TradeBrowse<CallOptionTrade> {

    @Override
    public Class<CallOptionTrade> getTradeClass() {
        return CallOptionTrade.class;
    }
}