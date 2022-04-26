package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.components.pnl.PnlCalculationBean;
import com.gcs.gcsplatform.web.components.trade.CopyLiveTradeBean;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.gcs.gcsplatform.web.screens.trade.brokeragefragment.TradeBrokerageFragment;
import com.gcs.gcsplatform.web.screens.trade.counterpartiesbrokersfragment.TradeCounterpartiesBrokersFragment;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.CurrencyField;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.Form;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.global.UserSession;
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.isDateInCurrentMonth;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
public class ClosedTradeEdit extends TradeEdit<ClosedTrade> {

    protected boolean isNewDailyBlotterTrade;

    @Inject
    protected PnlCalculationBean pnlCalculationBean;
    @Inject
    protected CopyLiveTradeBean copyLiveTradeBean;
    @Inject
    protected TimeSource timeSource;
    @Inject
    protected DateField<Date> maturityDateField;
    @Inject
    protected CurrencyField<BigDecimal> buyPnlField;
    @Inject
    protected CurrencyField<BigDecimal> sellPnlField;
    @Inject
    private UserSession userSession;
    @Inject
    private TextArea<String> notesField;
    @Inject
    private Form datesForm;
    @Inject
    private TradeCounterpartiesBrokersFragment counterpartiesBrokersFragment;
    @Inject
    private TradeBrokerageFragment brokerageFragment;

    public void setNewDailyBlotterTrade(boolean isNewDailyBlotterTrade) {
        this.isNewDailyBlotterTrade = isNewDailyBlotterTrade;
    }

    @Override
    protected void onAfterShow(AfterShowEvent event) {
        super.onAfterShow(event);
        repoCurrencyLookupPickerField.addValueChangeListener(this::onRepoCurrencyLookupPickerFieldValueChange);
        updatePnlCurrencySign();
        maturityDateField.setEditable(true);
        ClosedTrade trade = getEditedEntity();
        if (PersistenceHelper.isNew(trade) && isNewDailyBlotterTrade) {
            trade.setInvoiceDate(timeSource.currentTimestamp());
        }

        Date invoiceDate = trade.getInvoiceDate();


        //if (StringUtils.contains(userSession.getRoles().toString(),"Broker,")) {

        if (!isDateInCurrentMonth(invoiceDate))
        {
            descriptionForm.setEditable(false);
            counterpartiesBrokersFragment.getFragment().setEnabled(false);
            brokerageFragment.getFragment().setEnabled(false);
            datesForm.setEditable(false);
        }
        
        //}


    }

    @Inject
    private Form descriptionForm;

    protected void onRepoCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (event.isUserOriginated()) {
            updatePnlCurrencySign();
        }
    }

    @Override
    protected void onBondCurrencyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        super.onBondCurrencyLookupPickerFieldValueChange(event);
        if (event.isUserOriginated()) {
            updatePnlCurrencySign();
        }
    }

    protected void updatePnlCurrencySign() {
        String currency = tradeDc.getItem().getCurrency();
        boolean currencyIsNotBlank = StringUtils.isNotBlank(currency);
        if (currencyIsNotBlank) {
            buyPnlField.setCurrency(currency);
            sellPnlField.setCurrency(currency);
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
                || property.equals("repoCurrency")
                || property.equals("bondCurrency")
                || property.equals("buybrokerage")
                || property.equals("sellbrokerage")
                || property.equals("buyCommissionOverride")
                || property.equals("sellCommissionOverride")
                || property.equals("buyerCash")
                || property.equals("sellerCash")) {
            pnlCalculationBean.updatePnl(tradeDc.getItem());
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        ClosedTrade trade = getEditedEntity();
        DataContext dataContext = getDataContext();
        if (PersistenceHelper.isNew(trade)) {
            if (isNewDailyBlotterTrade) {
                copyLiveTradeBean.create(trade, dataContext);
            }
        } else {
            copyLiveTradeBean.update(trade, dataContext);
        }
    }
}