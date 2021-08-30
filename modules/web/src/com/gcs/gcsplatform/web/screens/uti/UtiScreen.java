package com.gcs.gcsplatform.web.screens.uti;

import javax.inject.Inject;

import com.gcs.gcsplatform.service.UtiService;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_UtiScreen")
@UiDescriptor("uti-screen.xml")
public class UtiScreen extends Screen {

    @Inject
    protected UtiService utiService;

    @Inject
    protected TextField<String> utiField;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        String utiValue = utiService.generateUti();
        utiField.setValue(utiValue);
    }

    @Subscribe("okButton")
    protected void onOkButtonClick(Button.ClickEvent event) {
        closeWithDefaultAction();
    }
}