package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.components.UpdateCorrespondingLiveTradeBean;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.gcs.gcsplatform.web.screens.trade.datesfragment.DatesFragment;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
public class ClosedTradeEdit extends TradeEdit<ClosedTrade> {

    @Inject
    protected DatesFragment datesFragment;

    @Inject
    protected UpdateCorrespondingLiveTradeBean updateCorrespondingLiveTradeBean;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        datesFragment.getMaturityDateField().setEditable(true);
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        updateCorrespondingLiveTradeBean.update(getEditedEntity(), getDataContext());
    }
}