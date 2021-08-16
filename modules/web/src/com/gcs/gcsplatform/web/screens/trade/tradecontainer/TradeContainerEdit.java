package com.gcs.gcsplatform.web.screens.trade.tradecontainer;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.CurrencyField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiDescriptor("trade-container-edit.xml")
@EditedEntityContainer("tradeContainerDc")
@LoadDataBeforeShow
public abstract class TradeContainerEdit<T extends TradeContainer> extends StandardEditor<T> {

    protected String initialWindowCaption;
    protected boolean isNew;

    @Inject
    protected PnlCalculationBean pnlCalculationBean;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Notifications notifications;

    @Inject
    protected LookupPickerField<Currency> currencyLookupPickerField;
    @Inject
    protected LookupPickerField<Currency> tradeCurrencyLookupPickerField;
    @Inject
    protected CurrencyField<BigDecimal> buyPnlField;
    @Inject
    protected CurrencyField<BigDecimal> sellPnlField;

    @Inject
    protected InstancePropertyContainer<Trade> tradeDc;
    @Inject
    protected InstanceContainer<T> tradeContainerDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(currencyLookupPickerField, tradeDc, "currency", "currency");
        initFieldValueToStringPropertyMapping(tradeCurrencyLookupPickerField, tradeDc, "currency", "tradeCurrency");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        tradeCurrencyLookupPickerField.addValueChangeListener(this::onTradeCurrencyLookupPickerFieldValueChange);
        updatePnlCurrencySign();

        initialWindowCaption = getWindow().getCaption();
        isNew = PersistenceHelper.isNew(getEditedEntity());
        Trade trade = tradeDc.getItem();
        if (!isNew) {
            String traderef = trade.getTraderef();
            if (StringUtils.isNotBlank(traderef)) {
                getWindow().setCaption(initialWindowCaption + " - " + traderef);
            }
        }
    }

    protected void onTradeCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            updatePnlCurrencySign();
            pnlCalculationBean.updatePnl(tradeDc.getItem());
        }
    }

    protected void updatePnlCurrencySign() {
        String tradeCurrency = tradeDc.getItem().getTradeCurrency();
        boolean notBlank = StringUtils.isNotBlank(tradeCurrency);
        if (notBlank) {
            buyPnlField.setCurrency(tradeCurrency);
            sellPnlField.setCurrency(tradeCurrency);
        }
        buyPnlField.setShowCurrencyLabel(notBlank);
        sellPnlField.setShowCurrencyLabel(notBlank);
    }

    @Subscribe("startPriceField")
    protected void onStartPriceFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        if (event.isUserOriginated()) {
            pnlCalculationBean.updatePnl(tradeDc.getItem());
        }
    }

    @Subscribe("nominalField")
    protected void onNominalFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        if (event.isUserOriginated()) {
            pnlCalculationBean.updatePnl(tradeDc.getItem());
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

    public void setTrade(T tradeContainer) {
        T merged = getScreenData().getDataContext().merge(tradeContainer);
        tradeContainerDc.setItem(merged);
    }

    public void updateWindowCaption(String tradeRef) {
        getWindow().setCaption(initialWindowCaption + " - " + tradeRef);
    }

    public boolean isNew() {
        return isNew;
    }
}