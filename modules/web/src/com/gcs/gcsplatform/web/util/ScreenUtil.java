package com.gcs.gcsplatform.web.util;

import java.util.Objects;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.OptionsField;
import com.haulmont.cuba.gui.components.data.Options;

public class ScreenUtil {

    /**
     * Adds value change event to specified field which causes mapping from selected field value (entity) to a string
     * property of target entity.
     * <p>
     * Sets default field value by entity string property value.
     * <p>
     * The reason of this mapping is that trade entity does not have actual foreign keys to dealer/brokerage
     * category/counterparty entities and uses string properties instead.
     *
     * @param field              - Source field
     * @param entity             - Target entity
     * @param fieldPropertyName  - Property name in field entity (only string type property)
     * @param targetPropertyName - Property name in target entity (only string type property)
     * @param <V>                - Field type
     * @param <T>                - Entity type contained in field
     */
    public static <V extends HasValue<T> & OptionsField<T, T>, T extends Entity> void initFieldValueToStringPropertyMapping(
            V field, Entity entity, String fieldPropertyName, String targetPropertyName) {
        field.addValueChangeListener(entityValueChangeEvent -> {
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

        String entityStringValue = entity.getValue(targetPropertyName);
        T defaultFieldValue = options.getOptions()
                .filter(optionEntity -> Objects.equals(entityStringValue, optionEntity.getValue(fieldPropertyName)))
                .findFirst()
                .orElse(null);
        field.setValue(defaultFieldValue);
    }
}
