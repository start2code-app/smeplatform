package com.gcs.gcsplatform.web.components;

import java.util.Collection;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.PnlService;
import com.gcs.gcsplatform.web.screens.pnl.PnlChartScreen;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.OpenMode;
import org.springframework.stereotype.Component;

@Component(PnlChartBean.NAME)
public class PnlChartBean {

    public static final String NAME = "gcsplatform_PnlChartBean";

    @Inject
    private PnlService pnlService;
    @Inject
    private ScreenBuilders screenBuilders;

    /**
     * Builds PNL chart with specified trades.
     *
     * @param trades - Trade list
     * @param origin - Origin screen
     */
    public void showPnlChartScreen(Collection<Trade> trades, FrameOwner origin) {
        PnlChartScreen pnlChartScreen = screenBuilders.screen(origin)
                .withScreenClass(PnlChartScreen.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .build();

        Collection<Pnl> pnlByBroker = pnlService.getPnlByBroker(trades);
        pnlChartScreen.setPnlByBroker(pnlByBroker);

        Collection<Pnl> pnlByCounterparty = pnlService.getPnlByCounterparty(trades);
        pnlChartScreen.setPnlByCounterparty(pnlByCounterparty);

        pnlChartScreen.show();
    }
}
