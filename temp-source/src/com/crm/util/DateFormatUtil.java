package com.crm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间类型格式化工具类
 * 
 *
 */
public class DateFormatUtil {

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String SCHEME_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式 YYYYMMdd
     */
    public static String SCHEME_NO_SYMBOL_DATE = "YYYYMMdd";
    
    public static String SCHEME_SYMBOL_DATE = "YYYY-MM-dd";

    /**
     * 格式化时间
     * 
     * @param date
     *            时间
     * @param scheme
     *            时间格式
     * @return
     */
    public static String format(Date date, String scheme) {
        SimpleDateFormat sdf = new SimpleDateFormat(scheme);
        return sdf.format(date);
    }
    
    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static Integer getNowTimeStamp() {
        return Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
    }

}
