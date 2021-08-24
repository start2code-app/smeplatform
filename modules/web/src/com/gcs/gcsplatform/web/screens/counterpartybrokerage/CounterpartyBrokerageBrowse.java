package com.gcs.gcsplatform.web.screens.counterpartybrokerage;

import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.service.CategoryService;
import com.gcs.gcsplatform.web.components.InitialBrokerageBean;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_CounterpartyBrokerage.browse")
@UiDescriptor("counterparty-brokerage-browse.xml")
@LoadDataBeforeShow
public class CounterpartyBrokerageBrowse extends Screen {

    protected List<Category> categories;

    @Inject
    protected DataManager dataManager;
    @Inject
    protected CategoryService categoryService;
    @Inject
    protected InitialBrokerageBean initialBrokerageBean;

    @Inject
    protected CollectionPropertyContainer<CounterpartyBrokerage> brokeragesDc;

    @Inject
    protected GroupTable<Counterparty> counterpartiesTable;
    @Inject
    protected GroupTable<CounterpartyBrokerage> brokeragesTable;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        categories = categoryService.getCategories(ViewBuilder.of(Category.class)
                .addView(View.MINIMAL)
                .build());
    }

    @Subscribe(id = "counterpartiesDc", target = Target.DATA_CONTAINER)
    protected void onCounterpartiesDcItemChange(InstanceContainer.ItemChangeEvent<Counterparty> event) {
        if (event.getItem() != null) {
            initialBrokerageBean.enrichBrokerageWithInitialData(brokeragesDc.getMutableItems(), categories,
                    counterpartiesTable.getSingleSelected());
            brokeragesTable.expandAll();
            brokeragesTable.sort("brokerageType", Table.SortDirection.ASCENDING);
            brokeragesTable.sort("category", Table.SortDirection.ASCENDING);
        }
    }

    @Subscribe(id = "brokeragesDc", target = Target.DATA_CONTAINER)
    protected void onBrokeragesDcItemPropertyChange(
            InstanceContainer.ItemPropertyChangeEvent<CounterpartyBrokerage> event) {
        CounterpartyBrokerage brokerage = event.getItem();
        if (brokerage.getBrokerageValue() != null) {
            CounterpartyBrokerage committed = dataManager.commit(brokerage);
            brokeragesDc.replaceItem(committed);
        }
    }
}