package com.ama.don.admin.utils;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {

    public static String calcDaysAndHours(Timestamp start, Timestamp end) {
        if (start == null || end == null) {
            return null;
        }
        LocalDateTime startDT = start.toLocalDateTime();
        LocalDateTime endDT = end.toLocalDateTime();

        Duration duration = Duration.between(startDT, endDT);

        long totalHours = duration.toHours();
        long days = totalHours / 24;
        long hours = totalHours % 24;

        return days + "일 " + hours + "시간";
    }
}
