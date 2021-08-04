package com.gcs.gcsplatform.web.screens.trade.tradecontainer;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

@UiDescriptor("trade-container-edit.xml")
@EditedEntityContainer("tradeContainerDc")
@LoadDataBeforeShow
public abstract class TradeContainerEdit<T extends TradeContainer> extends StandardEditor<T> {

    protected String initialWindowCaption;

    @Inject
    protected InstancePropertyContainer<Trade> tradeDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        initialWindowCaption = getWindow().getCaption();

        if (!PersistenceHelper.isNew(getEditedEntity())) {
            String traderef = tradeDc.getItem().getTraderef();
            if (!StringUtils.isEmpty(traderef)) {
                updateWindowCaption(traderef);
            }
        }
    }

    protected void updateWindowCaption(String tradeRef) {
        getWindow().setCaption(initialWindowCaption + " - " + tradeRef);
    }
}