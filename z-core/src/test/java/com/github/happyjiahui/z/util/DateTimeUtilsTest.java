package com.github.happyjiahui.z.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhaojinbing
 * @Classname DateTimeUtilsTest
 * @Description TODO
 * @Date 2019/12/5 11:53
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
        String formatDateTime = DateTimeUtils.formatDateTime(DATE_TIME);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatDateTimeByPatternTest() {
        String formatDateTime = DateTimeUtils.formatDateTime(DATE_TIME, DatePattern.PURE_DATETIME_PATTERN);
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
}