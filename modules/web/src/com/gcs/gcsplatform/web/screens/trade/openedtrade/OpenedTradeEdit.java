package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.service.CloseTradeService;
import com.gcs.gcsplatform.web.screens.trade.trade.TradeFragment;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Fragments;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.DialogMode;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.StandardOutcome;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.edit")
@UiDescriptor("opened-trade-edit.xml")
@EditedEntityContainer("openedTradeDc")
@LoadDataBeforeShow
@DialogMode(forceDialog = true)
public class OpenedTradeEdit extends StandardEditor<OpenedTrade> {

    @Inject
    protected HBoxLayout tradeBox;
    @Inject
    protected Button closeReopenTradeBtn;
    @Inject
    protected Button closeTradeBtn;

    @Inject
    protected DataManager dataManager;
    @Inject
    protected Dialogs dialogs;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected CloseTradeService closeTradeService;
    @Inject
    protected Fragments fragments;

    @Inject
    protected InstanceContainer<OpenedTrade> openedTradeDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        TradeFragment tradeFragment = fragments.create(this, TradeFragment.class);
        tradeFragment.setTrade(openedTradeDc.getItem().getTrade());
        tradeBox.add(tradeFragment.getFragment());

        if (PersistenceHelper.isNew(getEditedEntity())) {
            closeReopenTradeBtn.setEnabled(false);
            closeTradeBtn.setEnabled(false);
        }
    }

    @Subscribe("closeReopenTradeBtn")
    public void onCloseReopenTradeBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("closeReopenTradeDialog.caption"))
                .withParameter(InputParameter.dateTimeParameter("maturityDate")
                        .withCaption(messageBundle.getMessage("maturityDate"))
                        .withRequired(true))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        Date maturityDate = inputDialogCloseEvent.getValue("maturityDate");
                        closeTradeService.closeReopen(getEditedEntity(), maturityDate);
                        OpenedTrade openedTrade = dataManager.reload(getEditedEntity(), ViewBuilder.of(
                                OpenedTrade.class)
                                .add("trade", View.LOCAL)
                                .addView(View.LOCAL)
                                .build());
                        openedTradeDc.setItem(openedTrade);
                        notifications.create(Notifications.NotificationType.TRAY)
                                .withDescription(messageBundle.getMessage("tradeClosedAndReopened"))
                                .show();
                    }
                })
                .show();
    }

    @Subscribe("closeTradeBtn")
    public void onCloseTradeBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("closeTradeDialog.caption"))
                .withParameter(InputParameter.dateTimeParameter("maturityDate")
                        .withCaption(messageBundle.getMessage("maturityDate"))
                        .withRequired(true))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        Date maturityDate = inputDialogCloseEvent.getValue("maturityDate");
                        closeTradeService.close(getEditedEntity(), maturityDate);
                        close(StandardOutcome.COMMIT);
                        notifications.create(Notifications.NotificationType.TRAY)
                                .withDescription(messageBundle.getMessage("tradeClosed"))
                                .show();
                    }
                })
                .show();
    }
}