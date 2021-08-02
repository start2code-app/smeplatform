package com.gcs.gcsplatform.web.screens.trade.trade.counterpartiesbrokers;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Dealer;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.AgentService;
import com.gcs.gcsplatform.service.BrokerageService;
import com.gcs.gcsplatform.web.screens.agent.AgentBrowse;
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
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeCounterpartiesBrokersFragment")
@UiDescriptor("trade-counterparties-brokers-fragment.xml")
public class TradeCounterpartiesBrokersFragment extends ScreenFragment {

    @Inject
    protected AgentService agentService;
    @Inject
    protected BrokerageService brokerageService;

    @Inject
    protected LookupPickerField<Dealer> buyBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> buyerLookupPickerField;
    @Inject
    protected LookupPickerField<Agent> buyerAgentLookupPickerField;
    @Inject
    protected LookupPickerField<Dealer> sellBrokerLookupPickerField;
    @Inject
    protected LookupPickerField<Counterparty> sellerLookupPickerField;
    @Inject
    protected LookupPickerField<Agent> sellerAgentLookupPickerField;

    @Inject
    protected InstanceContainer<Trade> tradeDc;
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

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        Trade trade = tradeDc.getItem();
        initFieldValueToStringPropertyMapping(buyBrokerLookupPickerField, trade,"dealer", "buybroker");
        initFieldValueToStringPropertyMapping(buyerLookupPickerField, trade,"counterparty", "buyer");
        initFieldValueToStringPropertyMapping(buyerAgentLookupPickerField, trade, "agent", "buyerAgent");
        initFieldValueToStringPropertyMapping(sellBrokerLookupPickerField, trade, "dealer", "sellbroker");
        initFieldValueToStringPropertyMapping(sellerLookupPickerField, trade, "counterparty", "seller");
        initFieldValueToStringPropertyMapping(sellerAgentLookupPickerField, trade, "agent", "sellerAgent");
        initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
        initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
    }

    @Subscribe("buyerAgentLookupPickerField")
    protected void onBuyerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            Agent agent = event.getValue();
            if (agent != null) {
                buyerLookupPickerField.setValue(agent.getCounterparty());
            }
            initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
        }
    }

    @Subscribe("buyerLookupPickerField")
    protected void onBuyerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
            buyerAgentLookupPickerField.clear();
            updateBuyerBrokerage();
        }
    }

    protected void updateBuyerBrokerage() {
        Trade trade = tradeDc.getItem();
        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }

        BigDecimal buyBrokerage = brokerageService.findBrokerageValue(trade.getBuyer(), trade.getCategory(),
                trade.getBrokerageType());
        trade.setBuybrokerage(buyBrokerage);
    }

    @Subscribe("sellerAgentLookupPickerField")
    protected void onSellerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            Agent agent = event.getValue();
            if (agent != null) {
                sellerLookupPickerField.setValue(agent.getCounterparty());
            }
            initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
        }
    }

    @Subscribe("sellerLookupPickerField")
    protected void onSellerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
            sellerAgentLookupPickerField.clear();
            updateSellerBrokerage();
        }
    }

    protected void updateSellerBrokerage() {
        Trade trade = tradeDc.getItem();
        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }

        BigDecimal sellBrokerage = brokerageService.findBrokerageValue(trade.getSeller(), trade.getCategory(),
                trade.getBrokerageType());
        trade.setSellbrokerage(sellBrokerage);
    }

    protected void initAgents(LookupPickerField<Counterparty> counterpartyField, LookupPickerField<Agent> agentField) {
        Counterparty counterparty = counterpartyField.getValue();
        List<Agent> agents = agentService.getAgents(counterparty, ViewBuilder.of(Agent.class)
                .add("counterparty", View.MINIMAL)
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