package com.gcs.gcsplatform.web.screens.trade.datesfragment;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_DatesFragment")
@UiDescriptor("dates-fragment.xml")
public class DatesFragment extends ScreenFragment {

    @Inject
    protected PnlCalculationBean pnlCalculationBean;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Notifications notifications;

    @Inject
    protected DateField<Date> maturityDateField;

    @Inject
    protected InstanceContainer<Trade> tradeDc;

    @Subscribe("valueDateField")
    protected void onValueDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (!event.isUserOriginated()) {
            return;
        }

        Date value = event.getValue();
        Date prevValue = event.getPrevValue();
        Trade trade = tradeDc.getItem();
        Date maturityDate = trade.getMaturityDate();

        if (value != null && maturityDate != null && value.after(maturityDate)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withDescription(messageBundle.getMessage("valueDate.validationMsg"))
                    .show();
            trade.setValueDate(prevValue);
            return;
        }
        pnlCalculationBean.updatePnl(trade);
    }

    @Subscribe("maturityDateField")
    protected void onMaturityDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (!event.isUserOriginated()) {
            return;
        }

        Date value = event.getValue();
        Date prevValue = event.getPrevValue();
        Trade trade = tradeDc.getItem();
        Date valueDate = trade.getValueDate();

        if (value != null && valueDate != null && value.before(valueDate)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withDescription(messageBundle.getMessage("maturityDate.validationMsg"))
                    .show();
            trade.setMaturityDate(prevValue);
            return;
        }
        pnlCalculationBean.updatePnl(trade);
    }

    public DateField<Date> getMaturityDateField() {
        return maturityDateField;
    }
}