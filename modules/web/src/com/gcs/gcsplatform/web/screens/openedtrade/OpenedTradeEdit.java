package com.gcs.gcsplatform.web.screens.openedtrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.service.CloseTradeService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;

@UiController("gcsplatform_OpenedTrade.edit")
@UiDescriptor("opened-trade-edit.xml")
@EditedEntityContainer("openedTradeDc")
@LoadDataBeforeShow
@DialogMode(forceDialog = true)
public class OpenedTradeEdit extends StandardEditor<OpenedTrade> {

    @Inject
    private DataManager dataManager;
    @Inject
    private Dialogs dialogs;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private CloseTradeService closeTradeService;

    @Inject
    private InstanceContainer<OpenedTrade> openedTradeDc;

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