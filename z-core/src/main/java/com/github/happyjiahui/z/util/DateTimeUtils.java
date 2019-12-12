package com.github.happyjiahui.z.util;

import static com.github.happyjiahui.z.util.DateConstants.SHANGHAI_ZONE_ID;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author zhaojinbing
 * @version 2019/12/3 13:53
 * @since 0.1
 */
public class DateTimeUtils {

    private DateTimeUtils() {}

    /**
     * 将java.util.Date转化为java.time.LocalDateTime
     *
     * @param date
     *            java.util.Date
     * @param zoneId
     *            时区id,可从ZoneId.getAvailableZoneIds()中获取
     * @return java.time.LocalDateTime
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
     * @return java.time.LocalDateTime
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
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(Long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将毫秒转化为java.time.LocalDateTime
     *
     * @param millis
     *            毫秒
     * @return java.time.LocalDateTime
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
     * @return java.time.LocalDateTime
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
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String timeStr) {
        return parseLocalDateTime(timeStr, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将时间戳转化为java.time.LocalDateTime
     *
     * @param timestamp
     *            java.sql.Timestamp
     * @param zoneId
     *            时区id
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(Timestamp timestamp, ZoneId zoneId) {
        long millis = timestamp.getTime();
        return parseLocalDateTime(millis, zoneId);
    }

    /**
     * 将时间戳转化为java.time.LocalDateTime（默认东八区）
     *
     * @param timestamp
     *            时间戳
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(Timestamp timestamp) {
        return parseLocalDateTime(timestamp, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换 {@link LocalDateTime} 为 {@link LocalDate}
     *
     * @param dateTime
     *            {@link LocalDateTime}
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }

    /**
     * 转换 {@link Date} 为 {@link LocalDate}
     *
     * @param date
     *            {@link Date}
     * @param zoneId
     *            时区id
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * 转换 {@link Date} 为 {@link LocalDate}
     *
     * @param date
     *            {@link Date}
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Date date) {
        return parseLocalDate(date, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换毫秒为 {@link LocalDate}
     *
     * @param millis
     *            毫秒
     * @param zoneId
     *            时区id
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate();
    }

    /**
     * 转换毫秒为 {@link LocalDate}
     *
     * @param millis
     *            毫秒
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Long millis) {
        return parseLocalDate(millis, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换时间戳为 {@link LocalDate}
     * 
     * @param timestamp
     *            时间戳
     * @param zoneId
     *            时区id
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Timestamp timestamp, ZoneId zoneId) {
        long time = timestamp.getTime();
        return parseLocalDate(time, zoneId);
    }

    /**
     * 转换时间戳为 {@link LocalDate}
     *
     * @param timestamp
     *            时间戳
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(Timestamp timestamp) {
        long time = timestamp.getTime();
        return parseLocalDate(time, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换日期字符串为java.time.LocalDate
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, dateTimeFormatter);
    }

    /**
     * 转换日期字符串为java.time.LocalDate（默认日期格式为：yyyy-MM-dd）
     *
     * @param dateStr
     *            日期字符串
     * @return {@link LocalDate}
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return parseLocalDate(dateStr, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 转换日期字符串为java.time.LocalTime
     * 
     * @param timeStr
     *            时间字符串
     * @param pattern
     *            时间格式
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(timeStr, dateTimeFormatter);
    }

    /**
     * 转换日期字符串为java.time.LocalTime（默认时间格式为 HH:mm:ss）
     *
     * @param timeStr
     *            时间字符串
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return parseLocalTime(timeStr, DatePattern.NORM_TIME_PATTERN);
    }

    /**
     * 转换 {@link LocalDateTime} 为 {@link LocalTime}
     * 
     * @param dateTime
     *            {@link LocalDateTime}
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    /**
     * 转换 {@link Date} 为 {@link LocalTime}
     * 
     * @param date
     *            {@link Date}
     * @param zoneId
     *            时区id
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalTime();
    }

    /**
     * 转换 {@link Date} 为 {@link LocalTime}
     *
     * @param date
     *            {@link Date}
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Date date) {
        return parseLocalTime(date, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换毫秒为 {@link LocalTime}
     * 
     * @param millis
     *            毫秒
     * @param zoneId
     *            时区id
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalTime();
    }

    /**
     * 转换毫秒为 {@link LocalTime}
     *
     * @param millis
     *            毫秒
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Long millis) {
        return parseLocalTime(millis, SHANGHAI_ZONE_ID);
    }

    /**
     * 转换 {@link Timestamp} 为 {@link LocalTime}
     * 
     * @param timestamp
     *            {@link Timestamp}
     * @param zoneId
     *            时区id
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Timestamp timestamp, ZoneId zoneId) {
        return Instant.ofEpochMilli(timestamp.getTime()).atZone(zoneId).toLocalTime();
    }

    /**
     * 转换 {@link Timestamp} 为 {@link LocalTime}
     *
     * @param timestamp
     *            {@link Timestamp}
     * @return {@link LocalTime}
     */
    public static LocalTime parseLocalTime(Timestamp timestamp) {
        return parseLocalTime(timestamp, SHANGHAI_ZONE_ID);
    }

    /**
     * 将java.time.LocalDateTime转化为java.util.Date
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @param zoneId
     *            时区id
     * @return java.util.Date
     */
    public static Date parseDate(LocalDateTime dateTime, ZoneId zoneId) {
        return Date.from(dateTime.atZone(zoneId).toInstant());
    }

    /**
     * 将java.time.LocalDateTime转化为java.util.Date（默认东八区）
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return java.util.Date
     */
    public static Date parseDate(LocalDateTime dateTime) {
        return parseDate(dateTime, SHANGHAI_ZONE_ID);
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
     * @return java.util.Date
     */
    public static Date parseDate(String timeStr, String pattern, ZoneId zoneId) {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(timeStr, pattern);
        return parseDate(dateTime, zoneId);
    }

    /**
     * 将时间字符串转化为java.util.Date（默认时间格式为：yyyy-MM-dd HH:mm:ss，默认东八区）
     *
     * @param timeStr
     *            时间字符串
     * @return java.util.Date
     */
    public static Date parseDate(String timeStr) {
        return parseDate(timeStr, DatePattern.NORM_DATETIME_PATTERN, SHANGHAI_ZONE_ID);
    }

    /**
     * 将时间字符串转化为java.util.Date
     *
     * @param milliseconds
     *            毫秒
     * @param zoneId
     *            时区id
     * @return java.util.Date
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
     * @return java.util.Date
     */
    public static Date parseDate(Long milliseconds) {
        return parseDate(milliseconds, SHANGHAI_ZONE_ID);
    }

    /**
     * 将java.time.LocalDateTime转换为java.sql.Timestamp
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return java.sql.Timestamp
     */
    public static Timestamp parseTimestamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    /**
     * 将java.util.Date转换为java.sql.Timestamp
     * 
     * @param date
     *            java.util.Date
     * @return java.sql.Timestamp
     */
    public static Timestamp parseTimestamp(Date date) {
        return Timestamp.from(Instant.ofEpochMilli(date.getTime()));
    }

    /**
     * 将时间字符串转换为java.sql.Timestamp
     *
     * @param timeStr
     *            时间字符串
     * @return java.sql.Timestamp
     */
    public static Timestamp parseTimestamp(String timeStr) {
        return Timestamp.valueOf(timeStr);
    }

    /**
     * 将毫秒转换为java.sql.Timestamp
     * 
     * @param millis
     *            毫秒
     * @return java.sql.Timestamp
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
     * @return java.sql.Timestamp
     */
    public static Long parseMillis(LocalDateTime dateTime, ZoneId zoneId) {
        return dateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * 将java.time.LocalDateTime转换为毫秒 默认东八区
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return 毫秒
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
     * @return 毫秒
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
     * @return 毫秒
     */
    public static Long parseMillis(String timeStr) {
        return parseMillis(timeStr, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将java.util.Date转换为毫秒
     *
     * @param date
     *            java.util.Date
     * @return 毫秒
     */
    public static Long parseMillis(Date date) {
        return date.getTime();
    }

    /**
     * 将java.sql.Timestamp转换为毫秒
     * 
     * @param timestamp
     *            java.sql.Timestamp
     * @return 毫秒
     */
    public static Long parseMillis(Timestamp timestamp) {
        return timestamp.getTime();
    }

    /**
     * 格式化当前日期为日期字符串
     *
     * @param pattern
     *            日期格式
     * @return 日期字符串
     */
    public static String today(String pattern) {
        return formatLocalDate(LocalDate.now(), pattern);
    }

    /**
     * 格式化当前日期为日期字符串（默认时间格式为：yyyy-MM-dd）
     *
     * @return 日期字符串
     */
    public static String today() {
        return today(DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 格式化当前时间为时间字符串
     * 
     * @param pattern
     *            时间格式
     * @return 时间字符串
     */
    public static String now(String pattern) {
        return formatLocalDateTime(LocalDateTime.now(), pattern);
    }

    /**
     * 格式化当前时间为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     * 
     * @return 时间字符串
     */
    public static String now() {
        return now(DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化java.time.LocalDateTime为时间字符串
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @param pattern
     *            时间格式
     * @return 时间字符串
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化java.time.LocalDateTime为时间字符串
     *
     * @param dateTime
     *            java.time.LocalDateTime
     * @return 时间字符串
     */
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return formatLocalDateTime(dateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将java.time.LocalDate转换为日期字符串
     *
     * @param date
     *            {@link LocalDate}
     * @param pattern
     *            日期格式
     * @return 日期字符串
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将java.time.LocalDate转换为日期字符串（默认时间格式为：yyyy-MM-dd）
     *
     * @param date
     *            {@link LocalDate}
     * @return 日期字符串
     */
    public static String formatLocalDate(LocalDate date) {
        return formatLocalDate(date, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 格式化 {@link LocalTime} 为时间字符串
     *
     * @param localTime
     *            {@link LocalTime}
     * @param pattern
     *            时间格式化
     * @return 时间字符串
     */
    public static String formatLocalTime(LocalTime localTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localTime);
    }

    /**
     * 格式化 {@link LocalTime} 为时间字符串（默认时间格式为 HH:mm:ss）
     * 
     * @param localTime
     *            {@link LocalTime}
     * @return 时间字符串
     */
    public static String formatLocalTime(LocalTime localTime) {
        return formatLocalTime(localTime, DatePattern.NORM_TIME_PATTERN);
    }

    /**
     * 格式化java.util.Date为时间字符串
     *
     * @param date
     *            java.util.Date
     * @param pattern
     *            时间格式
     * @return 时间字符串
     */
    public static String formatDate(Date date, String pattern) {
        return DateTimeUtils.formatLocalDateTime(DateTimeUtils.parseLocalDateTime(date), pattern);
    }

    /**
     * 格式化java.util.Date为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     *            java.util.Date
     * @return 时间字符串
     */
    public static String formatDate(Date date) {
        return formatDate(date, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化毫秒为时间字符串
     *
     * @param millis
     *            毫秒
     * @param pattern
     *            时间格式
     * @return 时间字符串
     */
    public static String formatMillis(Long millis, String pattern) {
        LocalDateTime dateTime = parseLocalDateTime(millis);
        return formatLocalDateTime(dateTime, pattern);
    }

    /**
     * 格式化毫秒为时间字符串（默认时间格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param millis
     *            毫秒
     * @return 时间字符串
     */
    public static String formatMillis(Long millis) {
        return formatMillis(millis, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化时间戳为时间字符串
     *
     * @param timestamp
     *            时间戳
     * @param pattern
     *            时间格式
     * @return 时间字符串
     */
    public static String formatTimestamp(Timestamp timestamp, String pattern) {
        LocalDateTime dateTime = parseLocalDateTime(timestamp);
        return formatLocalDateTime(dateTime, pattern);
    }

    /**
     * 格式化时间戳为时间字符串
     *
     * @param timestamp
     *            时间戳
     * @return 时间字符串
     */
    public static String formatTimestamp(Timestamp timestamp) {
        return formatTimestamp(timestamp, DatePattern.NORM_DATETIME_PATTERN);
    }

}
