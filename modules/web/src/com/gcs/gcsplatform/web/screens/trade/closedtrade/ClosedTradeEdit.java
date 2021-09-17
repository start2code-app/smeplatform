package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.components.pnl.PnlCalculationBean;
import com.gcs.gcsplatform.web.components.trade.UpdateCorrespondingLiveTradeBean;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.components.CurrencyField;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
public class ClosedTradeEdit extends TradeEdit<ClosedTrade> {

    @Inject
    protected PnlCalculationBean pnlCalculationBean;

    @Inject
    protected DateField<Date> maturityDateField;
    @Inject
    protected CurrencyField<BigDecimal> buyPnlField;
    @Inject
    protected CurrencyField<BigDecimal> sellPnlField;

    @Inject
    protected UpdateCorrespondingLiveTradeBean updateCorrespondingLiveTradeBean;

    @Override
    protected void onAfterShow(AfterShowEvent event) {
        super.onAfterShow(event);
        tradeCurrencyLookupPickerField.addValueChangeListener(this::onTradeCurrencyLookupPickerFieldValueChange);
        updatePnlCurrencySign();
        maturityDateField.setEditable(true);
    }

    protected void onTradeCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            updatePnlCurrencySign();
        }
    }

    @Override
    protected void onCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        super.onCurrencyLookupPickerFieldValueChange(event);
        if (event.isUserOriginated()) {
            updatePnlCurrencySign();
        }
    }

    protected void updatePnlCurrencySign() {
        String tradeCurrency = tradeDc.getItem().getTradeCurrency();
        boolean currencyIsNotBlank = StringUtils.isNotBlank(tradeCurrency);
        if (currencyIsNotBlank) {
            buyPnlField.setCurrency(tradeCurrency);
            sellPnlField.setCurrency(tradeCurrency);
        }
        buyPnlField.setShowCurrencyLabel(currencyIsNotBlank);
        sellPnlField.setShowCurrencyLabel(currencyIsNotBlank);
    }

    @Subscribe(id = "tradeDc", target = Target.DATA_CONTAINER)
    protected void onTradeDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<ClosedTrade> event) {
        String property = event.getProperty();
        if (property.equals("nominal")
                || property.equals("startPrice")
                || property.equals("invoiceDate")
                || property.equals("numdays")
                || property.equals("tradeCurrency")
                || property.equals("buybrokerage")
                || property.equals("sellbrokerage")) {
            pnlCalculationBean.updatePnl(tradeDc.getItem());
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        updateCorrespondingLiveTradeBean.update(getEditedEntity(), getDataContext());
    }
}