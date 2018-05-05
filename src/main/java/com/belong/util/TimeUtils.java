package com.belong.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static String getFormatTime(String format,Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取month之后指定format的格式日期
     * @param format
     * @param monthNum
     * @return
     */
    public static String getCurTimeMonthNumAfterTimestamp(String format,int monthNum){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        calendar.set(year,month + monthNum,day);
        return simpleDateFormat.format(calendar.getTime());
    }
}
