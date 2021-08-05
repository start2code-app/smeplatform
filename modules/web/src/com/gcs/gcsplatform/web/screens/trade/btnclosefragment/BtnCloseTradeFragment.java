package com.gcs.gcsplatform.web.screens.trade.btnclosefragment;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.gcs.gcsplatform.service.CloseTradeService;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerEdit;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.StandardOutcome;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_BtnCloseTradeFragment")
@UiDescriptor("btn-close-trade-fragment.xml")
public class BtnCloseTradeFragment extends ScreenFragment {

    @Inject
    protected Button closeTradeBtn;

    @Inject
    protected Dialogs dialogs;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected CloseTradeService closeTradeService;

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {
        if (getHostTradeScreen().isNew()) {
            closeTradeBtn.setEnabled(false);
        }
    }

    @Subscribe("closeTradeBtn")
    protected void onCloseTradeBtnClick(Button.ClickEvent event) {
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
                        getHostScreen().close(StandardOutcome.DISCARD);
                        notifications.create(Notifications.NotificationType.TRAY)
                                .withDescription(messageBundle.getMessage("tradeClosed"))
                                .show();
                    }
                })
                .show();
    }

    protected TradeContainer getEditedEntity() {
        return (TradeContainer) getHostTradeScreen().getEditedEntity();
    }

    protected TradeContainerEdit getHostTradeScreen() {
        return (TradeContainerEdit) getHostScreen();
    }
}