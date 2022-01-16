package com.gcs.gcsplatform.web.screens.trade.btnclosereopenfragment;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.trade.CloseTradeBean;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getNextWorkingDay;

@UiController("gcsplatform_BtnCloseReopenTradeFragment")
@UiDescriptor("btn-close-reopen-trade-fragment.xml")
public class BtnCloseReopenTradeFragment extends ScreenFragment {

    @Inject
    protected Button closeReopenTradeBtn;

    @Inject
    protected Dialogs dialogs;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected CloseTradeBean closeTradeBean;

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {

        if (getHostTradeScreen().isNew()) {
            closeReopenTradeBtn.setEnabled(false);

        }
    }

    @Subscribe("closeReopenTradeBtn")
    protected void onCloseReopenTradeBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("closeReopenTradeDialog.caption"))
                .withParameter(InputParameter.dateParameter("maturityDate")
                        .withCaption(messageBundle.getMessage("maturityDate"))
                        .withRequired(true)
                        .withDefaultValue(getNextWorkingDay()))
                .withValidator(validationContext -> {
                    Date maturityDate = validationContext.getValue("maturityDate");
                    Date valueDate = getEditedEntity().getValueDate();
                    ValidationErrors validationErrors = new ValidationErrors();
                    if (valueDate != null && maturityDate != null && maturityDate.before(valueDate)) {
                        validationErrors.add(messageBundle.getMessage("maturityDate.validationMsg"));
                    }
                    return validationErrors;
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        Date maturityDate = inputDialogCloseEvent.getValue("maturityDate");
                        closeTradeBean.closeReopen(getEditedEntity(), maturityDate,
                                getHostTradeScreen().getDataContext());
                        getHostTradeScreen().updateWindowCaption();
                        notifications.create(Notifications.NotificationType.TRAY)
                                .withDescription(messageBundle.getMessage("tradeClosedAndReopened"))
                                .show();
                    }
                })
                .show();
    }

    protected Trade getEditedEntity() {
        return (Trade) getHostTradeScreen().getEditedEntity();
    }

    protected TradeEdit getHostTradeScreen() {
        return (TradeEdit) getHostScreen();
    }
}