package com.gcs.gcsplatform.web.screens.mainscreen;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.mainwindow.AppWorkArea;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.gui.components.mainwindow.WebAppWorkArea;
import com.haulmont.cuba.web.widgets.CubaMainTabSheet;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {

    @Inject
    protected AppWorkArea workArea;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        WebAppWorkArea webAppWorkArea = (WebAppWorkArea) workArea;
        CubaMainTabSheet tabSheet = (CubaMainTabSheet) webAppWorkArea.getTabbedWindowContainer();
        tabSheet.addStyleName("tab-colored");
    }
}