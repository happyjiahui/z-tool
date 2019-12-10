package com.github.happyjiahui.z.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhaojinbing
 */
public class DateTimeUtilsTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 12, 5, 16, 24, 50);
    private static final Long MILLIS = 1575534290000L;
    private static final Date DATE = new Date(MILLIS);
    private static final String YYYY_MM_DD_HH_MM_SS_PATTERN = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    private Matcher matcher(String formatDateTime, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(formatDateTime);
    }

    @Test
    public void formatNowTest() {
        String formatDateTime = DateTimeUtils.today();
        Matcher matcher = matcher(formatDateTime, YYYY_MM_DD_HH_MM_SS_PATTERN);
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void formatNowByPatternTest() {
        String formatDateTime = DateTimeUtils.today(DatePattern.NORM_DATETIME_PATTERN);
        Matcher matcher = matcher(formatDateTime, YYYY_MM_DD_HH_MM_SS_PATTERN);
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void formatDateTimeTest() {
        String formatDateTime = DateTimeUtils.formatLocalDateTime(DATE_TIME);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatDateTimeByPatternTest() {
        String formatDateTime = DateTimeUtils.formatLocalDateTime(DATE_TIME, DatePattern.PURE_DATETIME_PATTERN);
        Assert.assertEquals(formatDateTime, "20191205162450");
    }

    @Test
    public void formatMillisTest() {
        long milliseconds = DateTimeUtils.parseMillis(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatMillis(milliseconds);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatMillisByPatternTest() {
        long milliseconds = DateTimeUtils.parseMillis(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatMillis(milliseconds, DatePattern.PURE_DATETIME_PATTERN);
        Assert.assertEquals(formatDateTime, "20191205162450");
    }

    @Test
    public void formatTimestampTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatTimestamp(timestamp);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatTimestampByPatternTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatTimestamp(timestamp, DatePattern.PURE_DATETIME_PATTERN);
        Assert.assertEquals(formatDateTime, "20191205162450");
    }

    @Test
    public void parseLocalDateTimeTest() {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime("2019-12-05 16:24:50");
        LocalDateTime dateTime2 = LocalDateTime.of(2019, 12, 5, 16, 24, 50);
        Assert.assertEquals(dateTime, dateTime2);
    }

    @Test
    public void parseLocalDateTimeByPatternTest() {
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime("20191205162450", DatePattern.PURE_DATETIME_PATTERN);
        LocalDateTime dateTime2 = LocalDateTime.of(2019, 12, 5, 16, 24, 50);
        Assert.assertEquals(dateTime, dateTime2);
    }

    @Test
    public void parseLocalDateTimeFromMillisTest() {
        long milliseconds = DateTimeUtils.parseMillis(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(milliseconds);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseLocalDateTimeFromMillisByZoneIdTest() {
        Long milliseconds = DateTimeUtils.parseMillis(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(milliseconds, ZONE_ID);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseLocalDateTimeFromTimestampTest() {
        Timestamp timestamp = Timestamp.valueOf("2019-12-05 16:24:50");
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(timestamp);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseLocalDateTimeFromTimestampByZoneIdTest() {
        Timestamp timestamp = Timestamp.valueOf("2019-12-05 16:24:50");
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(timestamp, ZONE_ID);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseMillisFromLocalDateTimeTest() {
        Long millis = DateTimeUtils.parseMillis(DATE_TIME);
        Assert.assertEquals(millis, MILLIS);
    }

    @Test
    public void parseMillisFromStringTest() {
        Long millis = DateTimeUtils.parseMillis("2019-12-05 16:24:50");
        Assert.assertEquals(millis, MILLIS);
    }

    @Test
    public void parseMillisFromStringByZoneIdTest() {
        Long millis = DateTimeUtils.parseMillis("20191205162450", DatePattern.PURE_DATETIME_PATTERN);
        Assert.assertEquals(millis, MILLIS);
    }

    @Test
    public void parseMillisFromDateTest() {
        Long millis = DateTimeUtils.parseMillis(DATE);
        Assert.assertEquals(millis, MILLIS);
    }

    @Test
    public void parseMillisFromTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp("2019-12-05 16:24:50");
        Long millis = DateTimeUtils.parseMillis(timestamp);
        Assert.assertEquals(millis, MILLIS);
    }

    @Test
    public void parseTimestampFromStringTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp("2019-12-05 16:24:50");
        Long time = timestamp.getTime();
        Assert.assertEquals(time, MILLIS);
    }

    @Test
    public void parseTimestampFromDateTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE);
        Long time = timestamp.getTime();
        Assert.assertEquals(time, MILLIS);
    }

    @Test
    public void parseTimestampFromMillisTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(MILLIS);
        Long time = timestamp.getTime();
        Assert.assertEquals(time, MILLIS);
    }

    @Test
    public void parseTimestampFromDateTimeTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        Long time = timestamp.getTime();
        Assert.assertEquals(time, MILLIS);
    }

    @Test
    public void formatDateTest() {
        Date date = DateTimeUtils.parseDate(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatDate(date);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatDateByPatternTest() {
        Date date = DateTimeUtils.parseDate(DATE_TIME);
        String formatDateTime = DateTimeUtils.formatDate(date, DatePattern.NORM_DATETIME_PATTERN);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void parseLocalDateTimeFromDateTest() {
        Date date = DateTimeUtils.parseDate(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(date);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseLocalDateTimeFromDateByZoneIdTest() {
        Date date = DateTimeUtils.parseDate(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(date, ZONE_ID);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseDateFromStringTest() {
        Date date = DateTimeUtils.parseDate("2019-12-05 16:24:50");
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromStringByPatternAndZoneIdTest() {
        Date date = DateTimeUtils.parseDate("20191205162450", DatePattern.PURE_DATETIME_PATTERN, ZONE_ID);
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromMillisTest() {
        Date date = DateTimeUtils.parseDate(MILLIS);
        Assert.assertEquals(date, DATE);
    }

    private void assetLocalDate(LocalDate localDate) {
        int year = localDate.getYear();
        int monthValue = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        Assert.assertEquals(year, 2019);
        Assert.assertEquals(monthValue, 12);
        Assert.assertEquals(dayOfMonth, 5);
    }

    private void assetLocalTime(LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        Assert.assertEquals(hour, 16);
        Assert.assertEquals(minute, 24);
        Assert.assertEquals(second, 50);
    }

    @Test
    public void parseLocalDateByPatternTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate("2019-12-05", "yyyy-MM-dd");
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate("2019-12-05");
        assetLocalDate(localDate);
    }

    @Test
    public void formatLocalDateByPatternTest() {
        LocalDate localDate = LocalDate.of(2019, 12, 10);
        String date = DateTimeUtils.formatLocalDate(localDate, "yyyy-MM-dd");
        Assert.assertEquals(date, "2019-12-10");
    }

    @Test
    public void formatLocalDateTest() {
        LocalDate localDate = LocalDate.of(2019, 12, 10);
        String date = DateTimeUtils.formatLocalDate(localDate);
        Assert.assertEquals(date, "2019-12-10");
    }

    @Test
    public void parseLocalTimeByPatternTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime("16:24:50", "HH:mm:ss");
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime("16:24:50");
        assetLocalTime(localTime);
    }

    @Test
    public void formatLocalTimeTest() {
        LocalTime localTime = LocalTime.of(17, 54, 50);
        String time = DateTimeUtils.formatLocalTime(localTime);
        Assert.assertEquals(time, "17:54:50");
    }

    @Test
    public void formatLocalTimeByPatternTest() {
        LocalTime localTime = LocalTime.of(17, 54, 50);
        String time = DateTimeUtils.formatLocalTime(localTime, "HH:mm:ss");
        Assert.assertEquals(time, "17:54:50");
    }

    @Test
    public void parseLocalDateFromTimestampTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        LocalDate localDate = DateTimeUtils.parseLocalDate(timestamp);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromTimestampByZoneIdTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        LocalDate localDate = DateTimeUtils.parseLocalDate(timestamp, ZONE_ID);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromDateByZoneIdTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate(DATE, ZONE_ID);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromDateTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate(DATE);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromMillisByZoneIdTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate(MILLIS, ZONE_ID);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromMillisTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate(MILLIS);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalDateFromLocalDatetimeTest() {
        LocalDate localDate = DateTimeUtils.parseLocalDate(DATE_TIME);
        assetLocalDate(localDate);
    }

    @Test
    public void parseLocalTimeFromMillisByZoneIdTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime(MILLIS, ZONE_ID);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromMillisTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime(MILLIS);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromTimestampByZoneIdTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        LocalTime localTime = DateTimeUtils.parseLocalTime(timestamp, ZONE_ID);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromTimestampTest() {
        Timestamp timestamp = DateTimeUtils.parseTimestamp(DATE_TIME);
        LocalTime localTime = DateTimeUtils.parseLocalTime(timestamp);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromDateByZoneIdTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime(DATE, ZONE_ID);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromDateTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime(DATE);
        assetLocalTime(localTime);
    }

    @Test
    public void parseLocalTimeFromLocalDateTimeTest() {
        LocalTime localTime = DateTimeUtils.parseLocalTime(DATE_TIME);
        assetLocalTime(localTime);
    }

}