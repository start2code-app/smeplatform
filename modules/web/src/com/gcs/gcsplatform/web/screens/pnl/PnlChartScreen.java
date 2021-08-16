package com.gcs.gcsplatform.web.screens.pnl;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.pnl.chart.BrokerageTypeCount;
import com.gcs.gcsplatform.entity.pnl.chart.CategoryCount;
import com.gcs.gcsplatform.entity.pnl.chart.TotalPnl;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.gcs.gcsplatform.service.pnl.PnlChartService;
import com.gcs.gcsplatform.service.pnl.PnlService;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_PnlChartScreen")
@UiDescriptor("pnl-chart-screen.xml")
public class PnlChartScreen extends Screen {

    protected Collection<? extends TradeContainer> tradeContainers;

    @Inject
    protected PnlService pnlService;
    @Inject
    protected PnlChartService pnlChartService;

    @Inject
    protected GroupTable<Pnl> pnlByBrokerTable;
    @Inject
    protected GroupTable<Pnl> pnlByCounterpartyTable;
    @Inject
    protected HBoxLayout noDataBox;
    @Inject
    protected ScrollBoxLayout pnlScrollBox;

    @Inject
    protected CollectionContainer<Pnl> pnlByCounterpartyDc;
    @Inject
    protected CollectionContainer<Pnl> pnlByBrokerDc;
    @Inject
    protected CollectionContainer<TotalPnl> totalPnlByMonthDc;
    @Inject
    protected CollectionContainer<TotalPnl> totalPnlByBrokerDc;
    @Inject
    protected CollectionContainer<CategoryCount> categoryCountDc;
    @Inject
    protected CollectionContainer<BrokerageTypeCount> brokerageTypeCountDc;

    public void setTradeContainers(Collection<? extends TradeContainer> tradeContainers) {
        this.tradeContainers = tradeContainers;
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        Collection<Trade> trades = tradeContainers.stream()
                .map(TradeContainer::getTrade)
                .collect(Collectors.toList());

        if (trades.isEmpty()) {
            noDataBox.setVisible(true);
            pnlScrollBox.setVisible(false);
        }

        initPnlByBrokerTable(trades);
        initPnlByCounterpartyTable(trades);
        initCategoryChart(trades);
        initBrokerageTypeChart(trades);
        initTotalPnlByBrokerChart(pnlByBrokerDc.getItems());
        initTotalPnlByMonthChart(tradeContainers);
    }

    protected void initPnlByBrokerTable(Collection<Trade> trades) {
        Collection<Pnl> pnlByBroker = pnlService.getPnlByBroker(trades);
        pnlByBrokerDc.setItems(pnlByBroker);
        pnlByBrokerTable.expandAll();
    }

    protected void initPnlByCounterpartyTable(Collection<Trade> trades) {
        Collection<Pnl> pnlByCounterparty = pnlService.getPnlByCounterparty(trades);
        pnlByCounterpartyDc.setItems(pnlByCounterparty);
        pnlByCounterpartyTable.expandAll();
    }

    protected void initCategoryChart(Collection<Trade> trades) {
        Collection<CategoryCount> categoryCount = pnlChartService.getCategoryCount(trades);
        categoryCountDc.setItems(categoryCount);
    }

    protected void initBrokerageTypeChart(Collection<Trade> trades) {
        Collection<BrokerageTypeCount> brokerageTypeCount = pnlChartService.getBrokerageTypeCount(trades);
        brokerageTypeCountDc.setItems(brokerageTypeCount);
    }

    protected void initTotalPnlByBrokerChart(Collection<Pnl> pnls) {
        Collection<TotalPnl> totalPnlByBroker = pnlChartService.getTotalPnlByBroker(pnls);
        totalPnlByBrokerDc.setItems(totalPnlByBroker);
    }

    protected void initTotalPnlByMonthChart(Collection<? extends TradeContainer> tradeContainers) {
        Collection<TotalPnl> totalPnlByMonth = pnlChartService.getTotalPnlByMonth(tradeContainers);
        totalPnlByMonthDc.setItems(totalPnlByMonth);
    }
}