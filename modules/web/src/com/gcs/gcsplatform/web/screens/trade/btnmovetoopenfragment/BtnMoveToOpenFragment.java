package com.gcs.gcsplatform.web.screens.trade.btnmovetoopenfragment;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.trade.CloseTradeBean;
import com.gcs.gcsplatform.web.components.trade.MoveToOpenBean;
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
import com.haulmont.cuba.gui.screen.StandardOutcome;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;



@UiController("gcsplatform_BtnMoveToOpenFragment")
@UiDescriptor("btn-move-to-open-fragment.xml")
public class BtnMoveToOpenFragment extends ScreenFragment {

    @Inject
    protected Dialogs dialogs;
    @Inject
    protected Notifications notifications;
    @Inject
    private MoveToOpenBean moveToOpenBean;
    @Inject
    protected MessageBundle messageBundle;

    @Subscribe("MoveToOpenBtn")
    protected void onMoveToOpenBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("MoveTradeDialog.caption"))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        moveToOpenBean.moveToOpen(getEditedEntity(),getHostTradeScreen().getDataContext());

                        getHostScreen().close(StandardOutcome.DISCARD);

                        notifications.create(Notifications.NotificationType.TRAY)
                                .withDescription(messageBundle.getMessage("tradeMoved"))
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