package com.gcs.gcsplatform.web.util;

import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.AbstractAction;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.OptionsField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.components.data.Options;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.AppUI;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

public class ScreenUtil {

    /**
     * Adds value change event to specified field which causes mapping from selected field value (entity) to a string
     * property of target entity.
     * <p>
     * Sets default field value by entity string property value.
     * <p>
     * The reason of this mapping is that trade entity does not have actual foreign keys to broker/counterparty/trader
     * entities and uses string properties instead.
     *
     * @param field              Source field
     * @param container          Target entity container
     * @param fieldPropertyName  Property name in field entity (only string type property)
     * @param targetPropertyName Property name in target entity (only string type property)
     * @param <V>                Field type
     * @param <T>                Entity type contained in field
     */
    public static <V extends HasValue<T> & OptionsField<T, T>, T extends Entity> void initFieldValueToStringPropertyMapping(
            V field, InstanceContainer container, String fieldPropertyName, String targetPropertyName) {
        field.addValueChangeListener(entityValueChangeEvent -> {
            Entity entity = container.getItem();
            Entity fieldEntityValue = entityValueChangeEvent.getValue();
            String fieldStringValue = null;
            if (fieldEntityValue != null) {
                fieldStringValue = fieldEntityValue.getValue(fieldPropertyName);
            }
            entity.setValue(targetPropertyName, fieldStringValue);
        });

        Options<T> options = field.getOptions();
        if (options == null) {
            return;
        }

        Entity entity = container.getItem();
        String entityStringValue = entity.getValue(targetPropertyName);
        T defaultFieldValue = options.getOptions()
                .filter(optionEntity -> Objects.equals(entityStringValue, optionEntity.getValue(fieldPropertyName)))
                .findFirst()
                .orElse(null);
        field.setValue(defaultFieldValue);
    }

    public static Component generateDownloadLinkCell(Entity entity, String fileProperty) {
        UiComponents uiComponents = AppBeans.get(UiComponents.NAME);
        ExportDisplay exportDisplay = AppBeans.get(ExportDisplay.NAME);

        final FileDescriptor fd = entity.getValueEx(fileProperty);
        if (fd == null) {
            return uiComponents.create(Label.class);
        }

        if (PersistenceHelper.isNew(fd)) {
            Label label = uiComponents.create(Label.class);
            label.setValue(fd.getName());
            return label;
        } else {
            Button button = uiComponents.create(Button.class);
            button.setStyleName("link");
            button.setAction(new AbstractAction("download") {
                @Override
                public void actionPerform(Component component) {
                    exportDisplay.show(fd);
                }

                @Override
                public String getCaption() {
                    return fd.getName();
                }
            });
            return button;
        }
    }

    public static void showDateIntervalSelectionDialog(Screen screen, String caption,
            Consumer<InputDialog.InputDialogCloseEvent> closeListener, @Nullable Date defaultStartDate,
            @Nullable Date defaultEndDate) {
        Dialogs dialogs = AppUI.getCurrent().getDialogs();
        Messages messages = AppBeans.get(Messages.class);
        dialogs.createInputDialog(screen)
                .withCaption(caption)
                .withParameter(InputParameter.dateParameter("startDate")
                        .withCaption(messages.getMainMessage("dateIntervalDialog.startDate"))
                        .withDefaultValue(defaultStartDate))
                .withParameter(InputParameter.dateParameter("endDate")
                        .withCaption(messages.getMainMessage("dateIntervalDialog.endDate"))
                        .withDefaultValue(defaultEndDate))
                .withValidator(validationContext -> {
                    Date startDate = validationContext.getValue("startDate");
                    Date endDate = validationContext.getValue("endDate");
                    ValidationErrors errors = new ValidationErrors();
                    if (startDate != null && endDate != null && (startDate.after(endDate) || endDate.before(
                            startDate))) {
                        errors.add(messages.getMainMessage("dateIntervalDialog.validationMsg"));
                    }
                    return errors;
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeListener)
                .show();
    }
}
