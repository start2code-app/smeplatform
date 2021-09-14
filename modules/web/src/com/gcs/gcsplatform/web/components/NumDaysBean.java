package com.gcs.gcsplatform.web.components;

import com.gcs.gcsplatform.entity.HasNumdays;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;

@Component(NumDaysBean.NAME)
public class NumDaysBean {

    public static final String NAME = "gcsplatform_NumDaysBean";

    /**
     * Updates number of days between maturity date and value date.
     *
     * @param hasNumdays Entity
     */
    public void updateNumdays(HasNumdays hasNumdays) {
        hasNumdays.setNumdays(getDaysBetweenDates(hasNumdays.getMaturityDate(), hasNumdays.getValueDate()));
    }
}