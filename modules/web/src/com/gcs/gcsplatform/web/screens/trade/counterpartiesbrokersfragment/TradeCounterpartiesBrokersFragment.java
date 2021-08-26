package com.gcs.gcsplatform.web.screens.trade.counterpartiesbrokersfragment;

import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Dealer;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.AgentService;
import com.gcs.gcsplatform.web.components.BrokerageBean;
import com.gcs.gcsplatform.web.screens.agent.AgentBrowse;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeCounterpartiesBrokersFragment")
@UiDescriptor("trade-counterparties-brokers-fragment.xml")
public class TradeCounterpartiesBrokersFragment extends ScreenFragment {

    @Inject
    protected AgentService agentService;
    @Inject
    protected BrokerageBean brokerageBean;

    @Inject
    protected LookupPickerField<Dealer> buySplitBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Dealer> buyBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> buyerLookupPickerField;
    @Inject
    protected LookupPickerField<Agent> buyerAgentLookupPickerField;
    @Inject
    protected LookupPickerField<Dealer> sellSplitBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Dealer> sellBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> sellerLookupPickerField;
    @Inject
    protected LookupPickerField<Agent> sellerAgentLookupPickerField;

    @Inject
    protected InstancePropertyContainer<Trade> tradeDc;
    @Inject
    protected CollectionLoader<Dealer> dealersDl;
    @Inject
    protected CollectionLoader<Counterparty> counterpartiesDl;
    @Inject
    protected CollectionLoader<Agent> agentsDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        counterpartiesDl.load();
        dealersDl.load();
        agentsDl.load();
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(buySplitBrokerLookupPickerField, tradeDc, "dealer", "buySplitBroker");
        initFieldValueToStringPropertyMapping(buyBrokerLookupPickerField, tradeDc, "dealer", "buybroker");
        initFieldValueToStringPropertyMapping(buyerLookupPickerField, tradeDc, "counterparty", "buyer");
        initFieldValueToStringPropertyMapping(buyerAgentLookupPickerField, tradeDc, "agent", "buyerAgent");
        initFieldValueToStringPropertyMapping(sellSplitBrokerLookupPickerField, tradeDc, "dealer", "sellSplitBroker");
        initFieldValueToStringPropertyMapping(sellBrokerLookupPickerField, tradeDc, "dealer", "sellbroker");
        initFieldValueToStringPropertyMapping(sellerLookupPickerField, tradeDc, "counterparty", "seller");
        initFieldValueToStringPropertyMapping(sellerAgentLookupPickerField, tradeDc, "agent", "sellerAgent");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        buyerLookupPickerField.addValueChangeListener(this::onBuyerLookupPickerFieldValueChange);
        buyerAgentLookupPickerField.addValueChangeListener(this::onBuyerAgentLookupPickerFieldValueChange);
        sellerLookupPickerField.addValueChangeListener(this::onSellerLookupPickerFieldValueChange);
        sellerAgentLookupPickerField.addValueChangeListener(this::onSellerAgentLookupPickerFieldValueChange);

        initCounterparty();

        Trade trade = tradeDc.getItem();
        buySplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(trade.getBuySplit()));
        sellSplitBrokerLookupPickerField.setVisible(Boolean.TRUE.equals(trade.getSellSplit()));
    }

    /*
     * Initialize counterparty value based on agent, only in case if agent is filled but counterparty is not.
     */
    protected void initCounterparty() {
        Trade trade = tradeDc.getItem();

        if (StringUtils.isNotBlank(trade.getBuyerAgent()) && StringUtils.isBlank(trade.getBuyer())) {
            updateBuyerCounterparty();
        }
        if (StringUtils.isNotBlank(trade.getSellerAgent()) && StringUtils.isBlank(trade.getSeller())) {
            updateSellerCounterparty();
        }
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

    protected void onBuyerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            updateBuyerCounterparty();
        }
    }

    protected void updateBuyerCounterparty() {
        Agent agent = buyerAgentLookupPickerField.getValue();
        if (agent != null) {
            Counterparty counterparty = agent.getCounterparty();
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

        brokerageBean.updateBrokerage(trade);
    }

    protected void onSellerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            updateSellerCounterparty();
        }
    }

    protected void updateSellerCounterparty() {
        Agent agent = sellerAgentLookupPickerField.getValue();
        if (agent != null) {
            Counterparty counterparty = agent.getCounterparty();
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

        brokerageBean.updateBrokerage(trade);
    }

    protected void initAgents(LookupPickerField<Counterparty> counterpartyField, LookupPickerField<Agent> agentField) {
        Counterparty counterparty = counterpartyField.getValue();
        List<Agent> agents = agentService.getAgents(counterparty, ViewBuilder.of(Agent.class)
                .add("counterparty", viewBuilder -> viewBuilder
                        .add("billingCountry")
                        .add("cash")
                        .addView(View.MINIMAL))
                .addView(View.MINIMAL)
                .build());
        agentField.setOptionsList(agents);
    }

    @Install(to = "buyerAgentLookupPickerField.lookup", subject = "screenConfigurer")
    protected void buyerAgentLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        AgentBrowse agentBrowse = (AgentBrowse) screen;
        agentBrowse.setCounterparty(buyerLookupPickerField.getValue());
    }

    @Install(to = "sellerAgentLookupPickerField.lookup", subject = "screenConfigurer")
    protected void sellerAgentLookupPickerFieldLookupScreenConfigurer(Screen screen) {
        AgentBrowse agentBrowse = (AgentBrowse) screen;
        agentBrowse.setCounterparty(sellerLookupPickerField.getValue());
    }
}