package com.gcs.gcsplatform.service.report;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.haulmont.cuba.core.entity.Entity;

public interface ReportDataConversionService {

    String NAME = "gcsplatform_ReportDataConversionService";

    <V extends Entity<T>, T> Map<String, Object> entityToMap(V entity);

    <V extends Entity<T>, T> List<Map<String, Object>> entityCollectionToMapList(Collection<V> entities);
}