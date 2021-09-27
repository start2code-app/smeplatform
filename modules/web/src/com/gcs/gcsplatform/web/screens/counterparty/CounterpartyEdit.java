package com.gcs.gcsplatform.web.screens.counterparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.service.CategoryService;
import com.gcs.gcsplatform.web.components.brokerage.InitialBrokerageBean;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.components.data.value.ContainerValueSource;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.ScreenValidation;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_Counterparty.edit")
@UiDescriptor("counterparty-edit.xml")
@EditedEntityContainer("counterpartyDc")
@LoadDataBeforeShow
public class CounterpartyEdit extends StandardEditor<Counterparty> {

    protected Map<UUID, Component> validatableFields = new HashMap<>();

    @Inject
    protected DataManager dataManager;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected CategoryService categoryService;
    @Inject
    protected InitialBrokerageBean initialBrokerageBean;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected ScreenValidation screenValidation;

    @Inject
    protected CollectionContainer<Trader> tradersDc;
    @Inject
    protected CollectionLoader<Trader> tradersDl;
    @Inject
    protected CollectionContainer<CounterpartyBrokerage> brokeragesDc;
    @Inject
    protected CollectionLoader<CounterpartyBrokerage> brokeragesDl;

    @Inject
    protected GroupTable<CounterpartyBrokerage> brokeragesTable;
    @Inject
    protected GroupTable<Trader> tradersTable;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        tradersDl.setParameter("counterparty", getEditedEntity());
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

    @Subscribe("tradersTable.add")
    protected void onTradersTableAdd(Action.ActionPerformedEvent event) {
        Trader trader = dataContext.create(Trader.class);
        trader.setCounterparty(dataManager.getReference(Id.of(getEditedEntity())));
        tradersDc.getMutableItems().add(trader);
        tradersTable.setSelected(trader);
    }

    @Subscribe("tradersTable.remove")
    protected void onTradersTableRemove(Action.ActionPerformedEvent event) {
        Trader selected = tradersTable.getSingleSelected();
        dataContext.remove(selected);
        tradersDc.getMutableItems().remove(selected);
    }

    @Install(to = "tradersTable.name", subject = "columnGenerator")
    protected Component tradersTableNameColumnGenerator(Trader trader) {
        Component component = validatableFields.get(trader.getId());
        if (component == null) {
            TextField<String> textField = uiComponents.create(TextField.class);
            textField.setValueSource(new ContainerValueSource<>(tradersTable.getInstanceContainer(trader), "name"));
            validatableFields.put(trader.getId(), textField);
            return textField;
        }
        return component;
    }

    @Subscribe
    protected void onValidation(ValidationEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(validatableFields.values());
        event.addErrors(errors);
    }
}