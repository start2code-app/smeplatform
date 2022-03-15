package com.gcs.gcsplatform.web.screens.clpboard;

import java.text.SimpleDateFormat;
import java.util.Set;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;


@UiController("gcsplatform_FullCopyScreen")
@UiDescriptor("fullcopy-screen.xml")
public class FullCopyScreen extends Screen {

    @Inject
    private Notifications notifications;

    @Inject
    private TextArea<String> clipboardField;
    @Inject
    private Screens screens;

    SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

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
                sb.append(lt.getBuyer());
                sb.append("\t");
                sb.append(lt.getSeller());
                sb.append("\t");
                sb.append(DateFor.format(lt.getTradeDate()));
                sb.append("\t");
                sb.append(DateFor.format(lt.getValueDate()));
                sb.append("\t");
                if ( lt.getMaturityDate() != null )
                {
                    sb.append(DateFor.format(lt.getMaturityDate()));
                }
                else
                {
                    sb.append("  ");
                }
                sb.append("\t");
                sb.append(lt.getIsin());
                sb.append("\t");
                sb.append(lt.getBondDescription());
                sb.append("\t");
                sb.append(lt.getNominal());
                sb.append("\t");
                sb.append(lt.getRepoRate());
                sb.append("\t");
                sb.append(lt.getStartPrice());
                sb.append("\t");
                sb.append(lt.getHairCut());
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