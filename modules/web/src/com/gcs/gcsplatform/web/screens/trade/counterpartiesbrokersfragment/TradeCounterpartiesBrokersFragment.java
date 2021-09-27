package com.gcs.gcsplatform.web.screens.trade.counterpartiesbrokersfragment;

import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Broker;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.TraderService;
import com.gcs.gcsplatform.web.components.brokerage.BrokerageBean;
import com.gcs.gcsplatform.web.screens.counterparty.CounterpartyBrowse;
import com.gcs.gcsplatform.web.screens.trader.TraderBrowse;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeCounterpartiesBrokersFragment")
@UiDescriptor("trade-counterparties-brokers-fragment.xml")
public class TradeCounterpartiesBrokersFragment extends ScreenFragment {

    @Inject
    protected TraderService traderService;
    @Inject
    protected BrokerageBean brokerageBean;

    @Inject
    protected LookupPickerField<Broker> buySplitBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Broker> buyBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> buyerLookupPickerField;
    @Inject
    protected LookupPickerField<Trader> buyerAgentLookupPickerField;
    @Inject
    protected LookupPickerField<Broker> sellSplitBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Broker> sellBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> sellerLookupPickerField;
    @Inject
    protected LookupPickerField<Trader> sellerAgentLookupPickerField;

    @Inject
    protected InstanceContainer<Trade> tradeDc;
    @Inject
    protected CollectionLoader<Broker> brokersDl;
    @Inject
    protected CollectionLoader<Counterparty> counterpartiesDl;
    @Inject
    protected CollectionLoader<Trader> tradersDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        counterpartiesDl.load();
        brokersDl.load();
        tradersDl.load();
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(buySplitBrokerLookupPickerField, tradeDc, "name", "buySplitBroker");
        initFieldValueToStringPropertyMapping(buyBrokerLookupPickerField, tradeDc, "name", "buybroker");
        initFieldValueToStringPropertyMapping(buyerLookupPickerField, tradeDc, "counterparty", "buyer");
        initFieldValueToStringPropertyMapping(buyerAgentLookupPickerField, tradeDc, "name", "buyerAgent");
        initFieldValueToStringPropertyMapping(sellSplitBrokerLookupPickerField, tradeDc, "name", "sellSplitBroker");
        initFieldValueToStringPropertyMapping(sellBrokerLookupPickerField, tradeDc, "name", "sellbroker");
        initFieldValueToStringPropertyMapping(sellerLookupPickerField, tradeDc, "counterparty", "seller");
        initFieldValueToStringPropertyMapping(sellerAgentLookupPickerField, tradeDc, "name", "sellerAgent");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        buyerLookupPickerField.addValueChangeListener(this::onBuyerLookupPickerFieldValueChange);
        buyerAgentLookupPickerField.addValueChangeListener(this::onBuyerAgentLookupPickerFieldValueChange);
        sellerLookupPickerField.addValueChangeListener(this::onSellerLookupPickerFieldValueChange);
        sellerAgentLookupPickerField.addValueChangeListener(this::onSellerAgentLookupPickerFieldValueChange);

        updateBuyerCounterparty();
        updateSellerCounterparty();

        Trade trade = tradeDc.getItem();
        buySplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(trade.getBuySplit()));
        sellSplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(trade.getSellSplit()));
    }

    @Subscribe("buySplitCheckBox")
    protected void onBuySplitCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.isUserOriginated()) {
            buySplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(event.getValue()));
        }
    }

    @Subscribe("sellSplitCheckBox")
    protected void onSellSplitCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.isUserOriginated()) {
            sellSplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(event.getValue()));
        }
    }

    protected void onBuyerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Trader> event) {
        if (event.isUserOriginated()) {
            updateBuyerCounterparty();
        }
    }

    protected void updateBuyerCounterparty() {
        Trader trader = buyerAgentLookupPickerField.getValue();
        if (trader != null) {
            Counterparty counterparty = trader.getCounterparty();
            buyerLookupPickerField.setValue(counterparty);
            updateBuyerFields(counterparty);
        }
        initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
    }

    protected void onBuyerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
            buyerAgentLookupPickerField.clear();
            updateBuyerFields(event.getValue());
        }
    }

    protected void updateBuyerFields(Counterparty counterparty) {
        Trade trade = tradeDc.getItem();

        Boolean buyerCash = counterparty != null ? counterparty.getCash() : null;
        trade.setBuyerCash(buyerCash);

        String buyerLocation = counterparty != null ? counterparty.getBillingCountry() : null;
        trade.setBuyerLocation(buyerLocation);

        String buyerCode = counterparty != null ? counterparty.getBillingInfo1() : null;
        trade.setBuyerCode(buyerCode);

        String buyerInvoiceCode = counterparty != null ? counterparty.getBillingInfo3() : null;
        trade.setBuyerInvoiceCode(buyerInvoiceCode);

        brokerageBean.updateBrokerage(trade);
    }

    protected void onSellerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Trader> event) {
        if (event.isUserOriginated()) {
            updateSellerCounterparty();
        }
    }

    protected void updateSellerCounterparty() {
        Trader trader = sellerAgentLookupPickerField.getValue();
        if (trader != null) {
            Counterparty counterparty = trader.getCounterparty();
            sellerLookupPickerField.setValue(counterparty);
            updateSellerFields(counterparty);
        }
        initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
    }

    protected void onSellerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
            sellerAgentLookupPickerField.clear();
            updateSellerFields(event.getValue());
        }
    }

    protected void updateSellerFields(Counterparty counterparty) {
        Trade trade = tradeDc.getItem();

        Boolean sellerCash = counterparty != null ? counterparty.getCash() : null;
        trade.setSellerCash(sellerCash);

        String sellerLocation = counterparty != null ? counterparty.getBillingCountry() : null;
        trade.setSellerLocation(sellerLocation);

        String sellerCode = counterparty != null ? counterparty.getBillingInfo1() : null;
        trade.setSellerCode(sellerCode);

        String sellerInvoiceCode = counterparty != null ? counterparty.getBillingInfo3() : null;
        trade.setSellerInvoiceCode(sellerInvoiceCode);

        brokerageBean.updateBrokerage(trade);
    }

    protected void initAgents(LookupPickerField<Counterparty> counterpartyField, LookupPickerField<Trader> agentField) {
        Counterparty counterparty = counterpartyField.getValue();
        List<Trader> traders = traderService.getTraders(counterparty, ViewBuilder.of(Trader.class)
                .add("counterparty", View.LOCAL)
                .addView(View.MINIMAL)
                .build());
        agentField.setOptionsList(traders);
    }

    @Install(to = "buyerAgentLookupPickerField.lookup", subject = "screenConfigurer")
    protected void buyerAgentLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        TraderBrowse traderBrowse = (TraderBrowse) screen;
        traderBrowse.setCounterparty(buyerLookupPickerField.getValue());
    }

    @Install(to = "sellerAgentLookupPickerField.lookup", subject = "screenConfigurer")
    protected void sellerAgentLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        TraderBrowse traderBrowse = (TraderBrowse) screen;
        traderBrowse.setCounterparty(sellerLookupPickerField.getValue());
    }

    @Install(to = "buyerLookupPickerField.lookup", subject = "screenConfigurer")
    protected void buyerLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        CounterpartyBrowse counterpartyBrowse = (CounterpartyBrowse) screen;
        counterpartyBrowse.setOnlyActive(true);
    }

    @Install(to = "sellerLookupPickerField.lookup", subject = "screenConfigurer")
    protected void sellerLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        CounterpartyBrowse counterpartyBrowse = (CounterpartyBrowse) screen;
        counterpartyBrowse.setOnlyActive(true);
    }
}