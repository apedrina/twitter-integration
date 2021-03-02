package com.alissonpedrina.consumer.util;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtil {

    public static long parseToEpochMilli(Date date) {
        var strDateFormat = "MMM dd yyyy HH:mm:ss.SSS zzz";
        var dateFormat = new SimpleDateFormat(strDateFormat);
        var dateFormatted = dateFormat.format(date);
        var dtf = DateTimeFormatter.ofPattern(strDateFormat);
        var zdt = ZonedDateTime.parse(dateFormatted, dtf);
        return zdt.toInstant().toEpochMilli();

    }
}
