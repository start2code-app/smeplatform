package com.gcs.gcsplatform.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class DateUtils {

    @Nullable
    public static Long getDaysBetweenDates(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return null;
        }
        if (org.apache.commons.lang3.time.DateUtils.isSameDay(firstDate, secondDate)) {
            return 0L;
        }
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }
}
