package com.gcs.gcsplatform.web.screens.openedtrade;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
@LookupComponent("openedTradesTable")
@LoadDataBeforeShow
public class OpenedTradeBrowse extends StandardLookup<OpenedTrade> {
}