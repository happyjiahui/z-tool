package com.github.happyjiahui.z.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zhaojinbing
 * @Classname DateUtils
 * @Description TODO
 * @Date 2019/12/9 13:25
 */
public class DateUtils {

    private DateUtils() {

    }

    /**
     * 将java.time.LocalDateTime转化为java.util.Date
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @param zoneId
     *            时区id
     * @return
     */
    public static Date parseDate(LocalDateTime dateTime, ZoneId zoneId) {
        return Date.from(dateTime.atZone(zoneId).toInstant());
    }

    /**
     * 将java.time.LocalDateTime转化为java.util.Date（默认东八区）
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return
     */
    public static Date parseDate(LocalDateTime dateTime) {
        return parseDate(dateTime, DateConstants.SHANGHAI_ZONE_ID);
    }

    /**
     * 将时间字符串转化为java.util.Date
     *
     * @param timeStr
     *            时间字符串
     * @param pattern
     *            时间格式
     * @param zoneId
     *            时区id
     * @return
     */
    public static Date parseDate(String timeStr, String pattern, ZoneId zoneId) {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(timeStr, pattern);
        return parseDate(dateTime, zoneId);
    }

    /**
     * 将时间字符串转化为java.util.Date
     *
     * @param timeStr
     *            时间字符串
     * @param pattern
     *            时间格式
     * @return
     */
    public static Date parseDate(String timeStr, String pattern) {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(timeStr, pattern);
        return parseDate(dateTime, DateConstants.SHANGHAI_ZONE_ID);
    }

    /**
     * 将时间字符串转化为java.util.Date（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param timeStr
     *            时间字符串
     * @param zoneId
     *            时区id
     * @return
     */
    public static Date parseDate(String timeStr, ZoneId zoneId) {
        return parseDate(timeStr, DatePattern.NORM_DATETIME_PATTERN, zoneId);
    }

    /**
     * 将时间字符串转化为java.util.Date（默认时间格式为：yyyy-MM-dd HH:mm:ss，默认东八区）
     *
     * @param timeStr
     *            时间字符串
     * @return
     */
    public static Date parseDate(String timeStr) {
        return parseDate(timeStr, DatePattern.NORM_DATETIME_PATTERN, DateConstants.SHANGHAI_ZONE_ID);
    }

    /**
     * 将时间字符串转化为java.util.Date
     *
     * @param milliseconds
     *            毫秒
     * @param zoneId
     *            时区id
     * @return
     */
    public static Date parseDate(Long milliseconds, ZoneId zoneId) {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(milliseconds);
        return parseDate(dateTime, zoneId);
    }

    /**
     * 将时间字符串转化为java.util.Date（默认东八区）
     *
     * @param milliseconds
     *            毫秒
     * @return
     */
    public static Date parseDate(Long milliseconds) {
        return parseDate(milliseconds, DateConstants.SHANGHAI_ZONE_ID);
    }

    /**
     * 格式化java.util.Date为时间字符串
     *
     * @param date
     *            java.util.Date
     * @param pattern
     *            时间格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return DateTimeUtils.formatDateTime(DateTimeUtils.parseLocalDateTime(date), pattern);
    }

    /**
     * 格式化java.util.Date为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     *            java.util.Date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, DatePattern.NORM_DATETIME_PATTERN);
    }
}
