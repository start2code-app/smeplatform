package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.service.TradeService;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.gcs.gcsplatform.web.screens.trade.datesfragment.DatesFragment;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
public class ClosedTradeEdit extends TradeEdit<ClosedTrade> {

    @Inject
    protected DatesFragment datesFragment;

    @Inject
    protected MetadataTools metadataTools;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected TradeService tradeService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        datesFragment.getMaturityDateField().setEditable(true);
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        if (!isInvoiceDateInCurrentMonth()) {
            return;
        }
        LiveTrade liveTrade = tradeService.getCorrespondingLiveTrade(getEditedEntity(),
                ViewBuilder.of(LiveTrade.class)
                        .addView(View.LOCAL)
                        .build());
        if (liveTrade != null) {
            metadataTools.copy(getEditedEntity(), liveTrade);
            dataContext.setModified(dataContext.merge(liveTrade), true);
        }
    }

    protected boolean isInvoiceDateInCurrentMonth() {
        Date today = new Date();
        Date invoiceDate = getEditedEntity().getInvoiceDate();
        if (invoiceDate == null) {
            return false;
        }
        return DateUtils.truncatedEquals(today, invoiceDate, Calendar.MONTH);
    }
}