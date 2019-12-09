package com.github.happyjiahui.z.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 12, 5, 16, 24, 50);
    private static final Long MILLIS = 1575534290000L;
    private static final Date DATE = new Date(MILLIS);
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    @Test
    public void formatDateTest() {
        Date date = DateUtils.parseDate(DATE_TIME);
        String formatDateTime = DateUtils.formatDate(date);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void formatDateByPatternTest() {
        Date date = DateUtils.parseDate(DATE_TIME);
        String formatDateTime = DateUtils.formatDate(date, DatePattern.NORM_DATETIME_PATTERN);
        Assert.assertEquals(formatDateTime, "2019-12-05 16:24:50");
    }

    @Test
    public void parseLocalDateTimeFromDateTest() {
        Date date = DateUtils.parseDate(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(date);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseLocalDateTimeFromDateByZoneIdTest() {
        Date date = DateUtils.parseDate(DATE_TIME);
        LocalDateTime dateTime = DateTimeUtils.parseLocalDateTime(date, ZONE_ID);
        Assert.assertEquals(dateTime, DATE_TIME);
    }

    @Test
    public void parseDateFromStringTest() {
        Date date = DateUtils.parseDate("2019-12-05 16:24:50");
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromStringByPatternTest() {
        Date date = DateUtils.parseDate("20191205162450", DatePattern.PURE_DATETIME_PATTERN);
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromStringByPatternAndZoneIdTest() {
        Date date = DateUtils.parseDate("20191205162450", DatePattern.PURE_DATETIME_PATTERN, ZONE_ID);
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromStringByZoneIdTest() {
        Date date = DateUtils.parseDate("2019-12-05 16:24:50", ZONE_ID);
        Assert.assertEquals(date, DATE);
    }

    @Test
    public void parseDateFromMillisTest() {
        Date date = DateUtils.parseDate(MILLIS);
        Assert.assertEquals(date, DATE);
    }
}