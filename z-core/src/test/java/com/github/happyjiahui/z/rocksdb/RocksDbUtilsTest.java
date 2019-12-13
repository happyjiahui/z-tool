package com.github.happyjiahui.z.rocksdb;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.happyjiahui.z.exception.UtilException;
import com.github.happyjiahui.z.rocksDb.RocksDbHelp;

public class RocksDbUtilsTest {

    private static String dbName = "test";
    private static RocksDbHelp rocksDbHelp;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        rocksDbHelp = new RocksDbHelp(dbName, "rocksDbTest");
    }

    @Test
    public void test() {
        String value = "小明";
        rocksDbHelp.put(dbName, "a", value);
        String a = rocksDbHelp.get(dbName, "a");
        Assert.assertEquals(a, value);
    }

    @Test
    public void test2() {
        rocksDbHelp = new RocksDbHelp(dbName, "rocksDbTest");
        BigDecimal bigDecimal = BigDecimal.valueOf(20.12);
        rocksDbHelp.put(dbName, "b", bigDecimal);
        BigDecimal value = rocksDbHelp.get(dbName, "b");
        Assert.assertEquals(value, bigDecimal);
        rocksDbHelp.delete(dbName, "b");
        BigDecimal value2 = rocksDbHelp.get(dbName, "b");
        Assert.assertNull(value2);
    }

    @Test
    public void test3() {
        rocksDbHelp.close(dbName);
        thrown.expect(UtilException.class);
        thrown.expectMessage("没有找到对应的数据库");
        rocksDbHelp.put(dbName, "c", "hello world");
    }

}