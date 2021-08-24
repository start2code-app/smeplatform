package com.gcs.gcsplatform.web.screens.counterparty;

import java.util.List;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.service.CategoryService;
import com.gcs.gcsplatform.web.components.InitialBrokerageBean;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;

@UiController("gcsplatform_Counterparty.edit")
@UiDescriptor("counterparty-edit.xml")
@EditedEntityContainer("counterpartyDc")
@LoadDataBeforeShow
public class CounterpartyEdit extends StandardEditor<Counterparty> {

    @Inject
    protected DataManager dataManager;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected CategoryService categoryService;
    @Inject
    protected InitialBrokerageBean initialBrokerageBean;

    @Inject
    protected CollectionContainer<CounterpartyBrokerage> brokeragesDc;
    @Inject
    protected CollectionLoader<CounterpartyBrokerage> brokeragesDl;

    @Inject
    protected GroupTable<CounterpartyBrokerage> brokeragesTable;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        brokeragesDl.setParameter("counterparty", getEditedEntity());
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        List<Category> categories = categoryService.getCategories(ViewBuilder.of(Category.class)
                .addView(View.MINIMAL)
                .build());
        initialBrokerageBean.enrichBrokerageWithInitialData(brokeragesDc.getMutableItems(), categories,
                dataManager.getReference(Id.of(getEditedEntity())));
        brokeragesTable.expandAll();
        brokeragesTable.sort("brokerageType", Table.SortDirection.ASCENDING);
        brokeragesTable.sort("category", Table.SortDirection.ASCENDING);
    }

    @Subscribe(id = "brokeragesDc", target = Target.DATA_CONTAINER)
    protected void onBrokeragesDcItemPropertyChange(
            InstanceContainer.ItemPropertyChangeEvent<CounterpartyBrokerage> event) {
        CounterpartyBrokerage brokerage = event.getItem();
        boolean brokerageValueNotNull = brokerage.getBrokerageValue() != null;
        dataContext.setModified(dataContext.merge(brokerage), brokerageValueNotNull);
    }
}