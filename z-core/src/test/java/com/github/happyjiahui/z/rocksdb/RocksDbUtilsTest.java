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
        rocksDbHelp = new RocksDbHelp(dbName, "/tmp/rocksDbTest");
    }

    @Test
    public void testRunner() {
        for (int i=0; i<10; i++) {
            String key = "key_" + i;
            String value = "value_" + i;
            RocksDbRunner runner = new RocksDbRunner(key, value);
            new Thread(runner).start();
        }
    }

    @Test
    public void test() {
        String value = "小明";
        rocksDbHelp.put("a", value);
        String a = rocksDbHelp.get("a");
        Assert.assertEquals(a, value);
        StringBuilder builder = new StringBuilder();
        boolean exits = rocksDbHelp.exits("a", builder);
        Assert.assertTrue(exits);
    }

    @Test
    public void test2() {
        rocksDbHelp = new RocksDbHelp(dbName, "rocksDbTest");
        BigDecimal bigDecimal = BigDecimal.valueOf(20.12);
        rocksDbHelp.put("b", bigDecimal);
        BigDecimal value = rocksDbHelp.get("b");
        Assert.assertEquals(value, bigDecimal);
        rocksDbHelp.delete("b");
        BigDecimal value2 = rocksDbHelp.get("b");
        Assert.assertNull(value2);
    }

    @Test
    public void test3() {
        rocksDbHelp.close();
        thrown.expect(UtilException.class);
        thrown.expectMessage("没有找到对应的数据库");
        rocksDbHelp.put("c", "hello world");
    }

}