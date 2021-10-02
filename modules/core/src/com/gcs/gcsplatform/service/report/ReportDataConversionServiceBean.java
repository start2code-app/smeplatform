package com.gcs.gcsplatform.service.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.MetadataTools;
import org.springframework.stereotype.Service;

@Service(ReportDataConversionService.NAME)
public class ReportDataConversionServiceBean implements ReportDataConversionService {

    @Inject
    private EntityStates entityStates;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    protected Messages messages;

    @Override
    public <V extends Entity<T>, T> Map<String, Object> entityToMap(V entity) {
        Map<String, Object> metaPropertiesMap = new HashMap<>();
        if (entity != null) {
            DateFormat dateFormat = new SimpleDateFormat(messages.getMainMessage("dateFormat"));
            entity.getMetaClass().getProperties()
                    .forEach(metaProperty -> {
                        String propertyName = metaProperty.getName();
                        if (entityStates.isLoaded(entity, propertyName) && !metadataTools.isSystem(metaProperty)) {
                            Object value = entity.getValue(propertyName);
                            if (value instanceof Date) {
                                value = dateFormat.format(value);
                            }
                            metaPropertiesMap.put(propertyName, value);
                        }
                    });
        }
        return metaPropertiesMap;
    }

    @Override
    public <V extends Entity<T>, T> List<Map<String, Object>> entityCollectionToMapList(Collection<V> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(this::entityToMap)
                .collect(Collectors.toList());
    }
}