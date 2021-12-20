package com.gcs.gcsplatform.web.screens.trade;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.trade.ContractNumberBean;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;
import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiDescriptor("trade-edit.xml")
@EditedEntityContainer("tradeDc")
@LoadDataBeforeShow
public abstract class TradeEdit<T extends Trade> extends StandardEditor<T> {

    protected String initialWindowCaption;
    protected boolean isNew;

    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Notifications notifications;
    @Inject
    protected ContractNumberBean contractNumberBean;

    @Inject
    protected LookupPickerField<Currency> bondCurrencyLookupPickerField;
    @Inject
    protected LookupPickerField<Currency> repoCurrencyLookupPickerField;

    @Inject
    protected InstanceContainer<T> tradeDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        T trade = getEditedEntity();
        if (trade.getHairCut() == null) {
            trade.setHairCut(BigDecimal.ZERO);
        }
        initFieldValueToStringPropertyMapping(bondCurrencyLookupPickerField, tradeDc, "currency", "bondCurrency");
        initFieldValueToStringPropertyMapping(repoCurrencyLookupPickerField, tradeDc, "currency", "repoCurrency");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        bondCurrencyLookupPickerField.addValueChangeListener(this::onBondCurrencyLookupPickerFieldValueChange);

        initialWindowCaption = getWindow().getCaption();
        isNew = PersistenceHelper.isNew(trade);
        if (!isNew) {
            updateWindowCaption();
        } else {
            trade.setTraderef(contractNumberBean.getNextTradeRef());
        }
    }

    protected void onBondCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            repoCurrencyLookupPickerField.setValue(event.getValue());
        }
    }

    public void updateWindowCaption() {
        String traderef = getEditedEntity().getTraderef();
        String isin = getEditedEntity().getIsin();
        if (StringUtils.isNotBlank(traderef) && StringUtils.isNotBlank(isin)) {
            getWindow().setCaption(initialWindowCaption + " - " + traderef + " - " + isin);
        } else if (StringUtils.isNotBlank(traderef)) {
            getWindow().setCaption(initialWindowCaption + " - " + traderef);
        }
    }

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
        updateNumDays();
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
        updateNumDays();
    }

    protected void updateNumDays() {
        Trade trade = tradeDc.getItem();
        trade.setNumdays(getDaysBetweenDates(trade.getMaturityDate(), trade.getValueDate()));
    }

    public boolean isNew() {
        return isNew;
    }

    public DataContext getDataContext() {
        return getScreenData().getDataContext();
    }
}