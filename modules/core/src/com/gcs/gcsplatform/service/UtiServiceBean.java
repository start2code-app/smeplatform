package com.gcs.gcsplatform.service;

import java.util.Calendar;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.UtiConfig;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import org.springframework.stereotype.Service;

@Service(UtiService.NAME)
public class UtiServiceBean implements UtiService {

    private static final String UTI_SEQUENCE = "utiSequence";

    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;
    @Inject
    private UtiConfig utiConfig;

    @Override
    public String generateUti() {
        Calendar cal = Calendar.getInstance();
        return utiConfig.getUtiPrefix()
                + cal.get(Calendar.YEAR)
                + String.format("%02d", cal.get(Calendar.MONTH) + 1)
                + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))
                + getNextUti();
    }

    private String getNextUti() {
        long nextUti = uniqueNumbersAPI.getNextNumber(UTI_SEQUENCE);
        return String.format(utiConfig.getUtiGenerationFormat(), nextUti);
    }
}