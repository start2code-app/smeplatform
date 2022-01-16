package com.gcs.gcsplatform.web.screens.clpboard;

import java.util.Set;
import javax.inject.Inject;


import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.UtiService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextArea;

import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;


@UiController("gcsplatform_SimpleCopyScreen")
@UiDescriptor("simplecopy-screen.xml")
public class SimpleCopyScreen extends Screen {

    @Inject
    private Notifications notifications;

    @Inject
    private TextArea<String> clipboardField;
    @Inject
    private Screens screens;

    public void setSelected(Set<Trade> selected)
    {
        if (selected.isEmpty()) {

            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Please make a selection(s)"
                    )
                    .show();
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            for (Trade lt : selected) {
                sb.append(lt.getIsin());
                sb.append("\t");
                sb.append(lt.getBondDescription());
                sb.append("\t");
                sb.append(lt.getNominal());
                sb.append("\n");

            }

            clipboardField.setValue(sb.toString());


        }

    }

    @Subscribe("okButton")
    protected void onOkButtonClick(Button.ClickEvent event) {
        closeWithDefaultAction();
    }
}