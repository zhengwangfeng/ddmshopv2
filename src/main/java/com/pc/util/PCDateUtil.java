package com.pc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PCDateUtil {
    // 各种时间格式
    public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static Date getDateBegin(Date date){
        Date d = null;
        try {
             d = date_sdf.parse(date_sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    public static Date getDateEnd(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTime(getDateBegin(date));
        cl.add(Calendar.HOUR, 23);
        cl.add(Calendar.MINUTE, 59);
        cl.add(Calendar.SECOND, 59);
        return cl.getTime();
    }
}
