package com.gcs.gcsplatform.web.screens.trade.tradecontainer.counterpartiesbrokers;

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
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
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
    protected AgentService agentService;
    @Inject
    protected BrokerageService brokerageService;

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
        Trade trade = tradeDc.getItem();

        initFieldValueToStringPropertyMapping(buySplitBrokerLookupPickerField, trade, "dealer", "buySplitBroker");
        initFieldValueToStringPropertyMapping(buyBrokerLookupPickerField, trade, "dealer", "buybroker");
        initFieldValueToStringPropertyMapping(buyerLookupPickerField, trade, "counterparty", "buyer");
        initFieldValueToStringPropertyMapping(buyerAgentLookupPickerField, trade, "agent", "buyerAgent");
        initFieldValueToStringPropertyMapping(sellSplitBrokerLookupPickerField, trade, "dealer", "sellSplitBroker");
        initFieldValueToStringPropertyMapping(sellBrokerLookupPickerField, trade, "dealer", "sellbroker");
        initFieldValueToStringPropertyMapping(sellerLookupPickerField, trade, "counterparty", "seller");
        initFieldValueToStringPropertyMapping(sellerAgentLookupPickerField, trade, "agent", "sellerAgent");

        initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
        initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);

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

    @Subscribe("buyerAgentLookupPickerField")
    protected void onBuyerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            Agent agent = event.getValue();
            if (agent != null) {
                Counterparty counterparty = agent.getCounterparty();
                buyerLookupPickerField.setValue(counterparty);
                updateBuyerFields(counterparty);
            }
            initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
        }
    }

    @Subscribe("buyerLookupPickerField")
    protected void onBuyerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(buyerLookupPickerField, buyerAgentLookupPickerField);
            buyerAgentLookupPickerField.clear();
            updateBuyerFields(event.getValue());
        }
    }

    protected void updateBuyerFields(Counterparty counterparty) {
        Trade trade = tradeDc.getItem();

        String buyerLocation = counterparty != null ? counterparty.getBillingCountry() : null;
        trade.setBuyerLocation(buyerLocation);

        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }

        String buyerCounterparty = counterparty != null ? counterparty.getCounterparty() : null;
        BigDecimal buyBrokerage = brokerageService.findBrokerageValue(buyerCounterparty, trade.getCategory(),
                trade.getBrokerageType());
        trade.setBuybrokerage(buyBrokerage);
    }

    @Subscribe("sellerAgentLookupPickerField")
    protected void onSellerAgentLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Agent> event) {
        if (event.isUserOriginated()) {
            Agent agent = event.getValue();
            if (agent != null) {
                Counterparty counterparty = agent.getCounterparty();
                sellerLookupPickerField.setValue(counterparty);
                updateSellerFields(counterparty);
            }
            initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
        }
    }

    @Subscribe("sellerLookupPickerField")
    protected void onSellerLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (event.isUserOriginated()) {
            initAgents(sellerLookupPickerField, sellerAgentLookupPickerField);
            sellerAgentLookupPickerField.clear();
            updateSellerFields(event.getValue());
        }
    }

    protected void updateSellerFields(Counterparty counterparty) {
        Trade trade = tradeDc.getItem();

        String sellerLocation = counterparty != null ? counterparty.getBillingCountry() : null;
        trade.setSellerLocation(sellerLocation);

        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }

        String sellerCounterparty = counterparty != null ? counterparty.getCounterparty() : null;
        BigDecimal sellBrokerage = brokerageService.findBrokerageValue(sellerCounterparty, trade.getCategory(),
                trade.getBrokerageType());
        trade.setSellbrokerage(sellBrokerage);
    }

    protected void initAgents(LookupPickerField<Counterparty> counterpartyField, LookupPickerField<Agent> agentField) {
        Counterparty counterparty = counterpartyField.getValue();
        List<Agent> agents = agentService.getAgents(counterparty, ViewBuilder.of(Agent.class)
                .add("counterparty", viewBuilder -> viewBuilder
                        .add("billingCountry")
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