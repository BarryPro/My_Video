package com.belong.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String getFormatTime(String format,Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
