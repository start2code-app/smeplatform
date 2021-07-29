package com.gcs.gcsplatform.web.screens.counterpartybrokerage;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;

@UiController("gcsplatform_CounterpartyBrokerage.browse")
@UiDescriptor("counterparty-brokerage-browse.xml")
@LookupComponent("counterpartyBrokeragesTable")
@LoadDataBeforeShow
public class CounterpartyBrokerageBrowse extends StandardLookup<CounterpartyBrokerage> {

    @Inject
    protected DataManager dataManager;

    @Subscribe("test")
    protected void onTestClick(Button.ClickEvent event) {
        List<Category> categories = dataManager.load(Category.class)
                .list();
        List<Counterparty> counterparties = dataManager.load(Counterparty.class)
                .list();
        for (Counterparty counterparty : counterparties) {
            if (counterparty.getGcGilt() == null) {
                continue;
            }
            for (Category category : categories) {
                String cat = category.getCategory();
                if (cat.equals("GILT")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcGilt()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecGilt()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("CORP")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcCorp()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecCorp()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("EM")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcEm()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecEm()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("SSA")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcSsa()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecSsa()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("EGB")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcEgb()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecEgb()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("EGBPIGS")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcEgbpigs()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecEgbpigs()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("UST")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcUst()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecUst()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                } else if (cat.equals("ABS")) {
                    CounterpartyBrokerage counterpartyBrokerageGc = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageGc.setCategory(category);
                    counterpartyBrokerageGc.setCounterparty(counterparty);
                    counterpartyBrokerageGc.setBrokerageValue(new BigDecimal(counterparty.getGcAbs()));
                    counterpartyBrokerageGc.setBrokerageType(CounterpartyBrokerageType.GC);

                    CounterpartyBrokerage counterpartyBrokerageSp = dataManager.create(CounterpartyBrokerage.class);
                    counterpartyBrokerageSp.setCategory(category);
                    counterpartyBrokerageSp.setCounterparty(counterparty);
                    counterpartyBrokerageSp.setBrokerageValue(new BigDecimal(counterparty.getSpecAbs()));
                    counterpartyBrokerageSp.setBrokerageType(CounterpartyBrokerageType.SPECIAL);

                    dataManager.commit(counterpartyBrokerageGc, counterpartyBrokerageSp);
                }
            }
        }
    }
}