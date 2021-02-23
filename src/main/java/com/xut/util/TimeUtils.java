package com.xut.util;

import java.util.Date;

public class TimeUtils {
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    public static int getBetweenDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return -1;
        }
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        int days = (int) (Math.abs(t1 - t2) / ONE_DAY);
        return days;
    }
}
