package com.thtf.zhhcxpt.kxzlb.common.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zhaojinbing
 * @Classname DateTimeUtils
 * @Description DateTime工具类 LocalDateTime没有时区，Instant是UTC时间线上的一个时刻。
 * @Date 2019/12/3 13:53
 */
public class DateTimeUtils {

    private static final ZoneId SHANGHAI_ZONE_ID = ZoneId.of("Asia/Shanghai");

    private DateTimeUtils() {}

    /**
     * 将java.util.Date转化为java.time.LocalDateTime
     *
     * @param date
     *            java.util.Date
     * @param zoneId
     *            时区id,可从ZoneId.getAvailableZoneIds()中获取
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Date date, ZoneId zoneId) {
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将java.util.Date转化为java.time.LocalDateTime
     *
     * @param date
     *            java.util.Date
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Date date) {
        return parseLocalDateTime(date, SHANGHAI_ZONE_ID);
    }

    /**
     * 将毫秒转化为java.time.LocalDateTime
     *
     * @param millis
     *            毫秒
     * @param zoneId
     *            时区id,可从ZoneId.getAvailableZoneIds()中获取
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将毫秒转化为java.time.LocalDateTime
     *
     * @param millis
     *            毫秒
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Long millis) {
        return Instant.ofEpochMilli(millis).atZone(SHANGHAI_ZONE_ID).toLocalDateTime();
    }

    /**
     * 将时间字符串转化为java.time.LocalDateTime
     *
     * @param timeStr
     *            日期字符串
     * @param pattern
     *            时间格式
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String timeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(timeStr, dateTimeFormatter);
    }

    /**
     * 将时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）转化为java.time.LocalDateTime
     *
     * @param timeStr
     *            日期字符串
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String timeStr) {
        return parseLocalDateTime(timeStr, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将时间戳转化为java.time.LocalDateTime（默认东八区）
     *
     * @param timestamp
     *            时间戳
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Timestamp timestamp) {
        long millis = timestamp.getTime();
        return parseLocalDateTime(millis);
    }

    /**
     * 将时间戳转化为java.time.LocalDateTime
     *
     * @param timestamp
     * @param zoneId
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Timestamp timestamp, ZoneId zoneId) {
        long millis = timestamp.getTime();
        return parseLocalDateTime(millis, zoneId);
    }

    /**
     * 将java.time.LocalDateTime转换为java.sql.Timestamp
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return
     */
    public static Timestamp parseTimestamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    /**
     * 将java.util.Date转换为java.sql.Timestamp
     * 
     * @param date
     *            java.util.Date
     * @return
     */
    public static Timestamp parseTimestamp(Date date) {
        return Timestamp.from(Instant.ofEpochMilli(date.getTime()));
    }

    /**
     * 将时间字符串转换为java.sql.Timestamp
     *
     * @param timeStr
     *            时间字符串
     * @return
     */
    public static Timestamp parseTimestamp(String timeStr) {
        return Timestamp.valueOf(timeStr);
    }

    /**
     * 将毫秒转换为java.sql.Timestamp
     * 
     * @param millis
     *            毫秒
     * @return
     */
    public static Timestamp parseTimestamp(Long millis) {
        return Timestamp.from(Instant.ofEpochMilli(millis));
    }

    /**
     * 将java.time.LocalDateTime转换为毫秒
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @param zoneId
     *            时区id
     * @return
     */
    public static Long parseMillis(LocalDateTime dateTime, ZoneId zoneId) {
        return dateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * 将java.time.LocalDateTime转换为毫秒 默认东八区
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return
     */
    public static Long parseMillis(LocalDateTime dateTime) {
        return parseMillis(dateTime, SHANGHAI_ZONE_ID);
    }

    /**
     * 将时间字符串转换为毫秒
     *
     * @param timeStr
     *            时间字符串
     * @param pattern
     *            时间格式
     * @return
     */
    public static Long parseMillis(String timeStr, String pattern) {
        LocalDateTime dateTime = parseLocalDateTime(timeStr, pattern);
        return parseMillis(dateTime);
    }

    /**
     * 将时间字符串转换为毫秒
     *
     * @param timeStr
     *            日期字符串
     * @return
     */
    public static Long parseMillis(String timeStr) {
        LocalDateTime dateTime = parseLocalDateTime(timeStr);
        return parseMillis(dateTime);
    }

    /**
     * 将java.util.Date转换为毫秒
     *
     * @param date
     * @return
     */
    public static Long parseMillis(Date date) {
        return date.getTime();
    }

    /**
     * 将java.sql.Timestamp转换为毫秒
     * 
     * @param timestamp
     *            java.sql.Timestamp
     * @return
     */
    public static Long parseMillis(Timestamp timestamp) {
        return timestamp.getTime();
    }

    /**
     * 格式化当前时间为时间字符串
     *
     * @param pattern
     *            时间格式
     * @return
     */
    public static String today(String pattern) {
        return formatDateTime(LocalDateTime.now(), pattern);
    }

    /**
     * 格式化当前时间为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String today() {
        return today(DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化java.time.LocalDateTime为时间字符串
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @param pattern
     *            时间格式
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化java.time.LocalDateTime为时间字符串
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化毫秒为时间字符串
     *
     * @param millis
     *            毫秒
     * @param pattern
     *            时间格式
     * @return
     */
    public static String formatMillis(Long millis, String pattern) {
        LocalDateTime dateTime = parseLocalDateTime(millis);
        return formatDateTime(dateTime, pattern);
    }

    /**
     * 格式化毫秒为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param millis
     *            毫秒
     * @return
     */
    public static String formatMillis(Long millis) {
        return formatMillis(millis, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化时间戳为时间字符串
     *
     * @param timestamp
     *            时间戳
     * @return
     */
    public static String formatTimestamp(Timestamp timestamp) {
        LocalDateTime dateTime = parseLocalDateTime(timestamp);
        return formatDateTime(dateTime);
    }

    /**
     * 格式化时间戳为时间字符串
     *
     * @param timestamp
     *            时间戳
     * @param pattern
     *            时间格式
     * @return
     */
    public static String formatTimestamp(Timestamp timestamp, String pattern) {
        LocalDateTime dateTime = parseLocalDateTime(timestamp);
        return formatDateTime(dateTime, pattern);
    }
}
