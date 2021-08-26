package com.gcs.gcsplatform.web.screens.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiDescriptor("trade-edit.xml")
@EditedEntityContainer("tradeDc")
@LoadDataBeforeShow
public abstract class TradeEdit<T extends Trade> extends StandardEditor<T> {

    protected String initialWindowCaption;
    protected boolean isNew;

    @Inject
    protected DataContext dataContext;

    @Inject
    protected LookupPickerField<Currency> currencyLookupPickerField;
    @Inject
    protected LookupPickerField<Currency> tradeCurrencyLookupPickerField;

    @Inject
    protected InstanceContainer<T> tradeDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(currencyLookupPickerField, tradeDc, "currency", "currency");
        initFieldValueToStringPropertyMapping(tradeCurrencyLookupPickerField, tradeDc, "currency", "tradeCurrency");

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

    @Subscribe("currencyLookupPickerField")
    protected void onCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            tradeCurrencyLookupPickerField.setValue(event.getValue());
        }
    }

    public void setTrade(T trade) {
        T merged = dataContext.merge(trade);
        tradeDc.setItem(merged);
    }

    public void updateWindowCaption(String tradeRef) {
        getWindow().setCaption(initialWindowCaption + " - " + tradeRef);
    }

    public boolean isNew() {
        return isNew;
    }
}