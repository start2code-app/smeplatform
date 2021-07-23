package com.gcs.gcsplatform.web.screens;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.RelativePathResource;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.login.LoginScreen;
import com.haulmont.cuba.web.gui.screen.ScreenDependencyUtils;
import com.vaadin.ui.Dependency;


@Route(path = "login", root = true)
@UiController("gcsplatform_LoginScreen")
@UiDescriptor("gcsplatform-login-screen.xml")
public class GcsPlatformLoginScreen extends LoginScreen {

    @Override
    public void onInit(InitEvent event) {
        super.onInit(event);
        loadStyles();
    }

    protected void loadStyles() {
        ScreenDependencyUtils.addScreenDependency(this,
                "vaadin://brand-login-screen/login.css", Dependency.Type.STYLESHEET);
    }

    @Subscribe("submit")
    public void onSubmit(Action.ActionPerformedEvent event) {
        login();
    }

    @Override
    protected void initLogoImage() {
        logoImage.setSource(RelativePathResource.class)
                .setPath("VAADIN/brand-login-screen/gcslogohome.png");
    }
}