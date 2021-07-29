package com.gcs.gcsplatform.web.screens.trade.trade;

import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.BrokerageCategory;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Dealer;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.AgentService;
import com.gcs.gcsplatform.web.screens.agent.AgentBrowse;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_TradeFragment")
@UiDescriptor("trade-fragment.xml")
public class TradeFragment extends ScreenFragment {


    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onBeforeShow(Screen.BeforeShowEvent event) {
        getScreenData().loadAll();
    }

}