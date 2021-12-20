package com.gcs.gcsplatform.web.screens.mainscreen;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.gui.components.mainwindow.AppWorkArea;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.WebConfig;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.gui.components.mainwindow.WebAppWorkArea;
import com.haulmont.cuba.web.widgets.CubaMainTabSheet;

@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {

    @Inject
    protected AppWorkArea workArea;
    @Inject
    protected Security security;
    @Inject
    protected WebConfig webConfig;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        WebAppWorkArea webAppWorkArea = (WebAppWorkArea) workArea;
        CubaMainTabSheet tabSheet = (CubaMainTabSheet) webAppWorkArea.getTabbedWindowContainer();
        if (tabSheet != null) {
            tabSheet.addStyleName("tab-colored");
        }
    }

    @Override
    protected void onAfterShow(AfterShowEvent event) {
        if (!security.isScreenPermitted(webConfig.getDefaultScreenId())) {
            return;
        }
        super.onAfterShow(event);
    }
}