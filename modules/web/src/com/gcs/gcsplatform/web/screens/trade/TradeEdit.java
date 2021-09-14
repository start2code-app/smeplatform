package com.gcs.gcsplatform.web.screens.trade;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.CurrencyField;
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
    protected LookupPickerField<Currency> currencyLookupPickerField;
    @Inject
    protected LookupPickerField<Currency> tradeCurrencyLookupPickerField;

    @Inject
    protected InstanceContainer<T> tradeDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(currencyLookupPickerField, tradeDc, "currency", "currency");
        initFieldValueToStringPropertyMapping(tradeCurrencyLookupPickerField, tradeDc, "currency", "tradeCurrency");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        currencyLookupPickerField.addValueChangeListener(this::onCurrencyLookupPickerFieldValueChange);


        initialWindowCaption = getWindow().getCaption();
        isNew = PersistenceHelper.isNew(getEditedEntity());
        if (!isNew) {
            updateWindowCaption();
        }
    }

    protected void onCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            tradeCurrencyLookupPickerField.setValue(event.getValue());
        }
    }

    public void updateWindowCaption() {
        String traderef = getEditedEntity().getTraderef();
        if (StringUtils.isNotBlank(traderef)) {
            getWindow().setCaption(initialWindowCaption + " - " + traderef);
        }
    }

    public boolean isNew() {
        return isNew;
    }

    public DataContext getDataContext() {
        return getScreenData().getDataContext();
    }


}