package com.gcs.gcsplatform.web.components.pnl;

import java.util.Collection;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.screens.pnl.PnlChartScreen;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.OpenMode;
import org.springframework.stereotype.Component;

@Component(PnlChartBean.NAME)
public class PnlChartBean {

    public static final String NAME = "gcsplatform_PnlChartBean";

    @Inject
    private ScreenBuilders screenBuilders;

    /**
     * Builds and shows PNL chart screen.
     *
     * @param frameOwner Frame owner
     * @param trades     Trades for PNL chart
     * @param caption    PNL screen caption (on main tab sheet)
     * @param heading    PNL screen heading
     */
    public void showPnlChartScreen(FrameOwner frameOwner, Collection<? extends Trade> trades, String caption,
            String heading) {
        PnlChartScreen pnlChartScreen = screenBuilders.screen(frameOwner)
                .withScreenClass(PnlChartScreen.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .build();
        pnlChartScreen.setTrades(trades);
        pnlChartScreen.setCaption(caption);
        pnlChartScreen.setHeading(heading);
        pnlChartScreen.show();
    }

    public void showPnlChartScreen(FrameOwner frameOwner, Collection<? extends Trade> trades, String caption) {
        showPnlChartScreen(frameOwner, trades, caption, null);
    }
}