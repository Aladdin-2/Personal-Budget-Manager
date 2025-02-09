package com.aladdin.personalbudgetcontrollerdemo2.utils;

import java.time.LocalDateTime;

public class DateTimeUtil {

    public static LocalDateTime getStartOfDay(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return LocalDateTime.of(
                year,
                month,
                day,
                (hour != null) ? hour : 0,
                (minute != null) ? minute : 0);
    }

    public static LocalDateTime getEndOfDay(Integer year, Integer month, Integer day) {
        return LocalDateTime.of(year, month, day, 23, 59);
    }
}
