package com.gcs.gcsplatform.web.screens.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.trade.TradeValidationBean;
import com.gcs.gcsplatform.web.events.TradeChangedEvent;
import com.gcs.gcsplatform.web.screens.uti.UtiScreen;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiDescriptor("trade-browse.xml")
@LookupComponent("tradesTable")
@LoadDataBeforeShow
public abstract class TradeBrowse<T extends Trade> extends StandardLookup<T> {

    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected TradeValidationBean tradeValidationBean;

    @Inject
    protected CollectionLoader<T> tradesDl;

    @Install(to = "tradesTable", subject = "styleProvider")
    protected String tradesTableStyleProvider(T entity, String property) {
        if (property == null) {
            if (tradeValidationBean.hasBlankRequiredFields(entity)) {
                return "v-table-row pink-row";
            }

            if (tradeValidationBean.hasZeroBrokerage(entity)) {
                return "v-table-row yellow-row";
            }
        }
        return null;
    }

    @Subscribe("utiBtn")
    protected void onUtiBtnClick(Button.ClickEvent event) {
        screenBuilders.screen(this)
                .withScreenClass(UtiScreen.class)
                .withOpenMode(OpenMode.DIALOG)
                .show();
    }

    @EventListener
    protected void onTradeChanged(TradeChangedEvent event) {
        tradesDl.load();
    }
}