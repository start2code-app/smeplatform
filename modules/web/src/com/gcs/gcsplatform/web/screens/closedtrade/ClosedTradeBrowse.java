package com.gcs.gcsplatform.web.screens.closedtrade;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;

@UiController("gcsplatform_ClosedTrade.browse")
@UiDescriptor("closed-trade-browse.xml")
@LookupComponent("closedTradesTable")
@LoadDataBeforeShow
public class ClosedTradeBrowse extends StandardLookup<ClosedTrade> {
}