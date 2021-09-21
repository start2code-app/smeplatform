package com.gcs.gcsplatform.service.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.haulmont.cuba.core.entity.Entity;
import org.springframework.stereotype.Service;

@Service(ReportDataConversionService.NAME)
public class ReportDataConversionServiceBean implements ReportDataConversionService {

    @Override
    public <V extends Entity<T>, T> List<Map<String, Object>> entityToMapList(V entity) {
        return Collections.singletonList(entityToMap(entity));
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

    private <T> Map<String, Object> entityToMap(Entity<T> entity) {
        Map<String, Object> metaPropertiesMap = new HashMap<>();
        entity.getMetaClass().getProperties()
                .forEach(metaProperty -> {
                    String propertyName = metaProperty.getName();
                    metaPropertiesMap.put(propertyName, entity.getValue(propertyName));
                });
        return metaPropertiesMap;
    }
}