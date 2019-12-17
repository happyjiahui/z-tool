package com.github.happyjiahui.z.rocksdb;

import java.math.BigDecimal;

import com.github.happyjiahui.z.rocksDb.SimpleRocksDBException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.happyjiahui.z.exception.UtilException;
import com.github.happyjiahui.z.rocksDb.SimpleRocksDB;

public class RocksDbUtilsTest {

    private static String dbName = "test";
    private static SimpleRocksDB simpleRocksDB;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        simpleRocksDB = new SimpleRocksDB(dbName, "/tmp/rocksDbTest");
    }

    @Test
    public void testDiffDbRunner() {
        for (int i=0; i<10; i++) {
            String dbName = "test_db_diff_" + i;
            String key = "key_" + i;
            String value = "value_" + i;
            RocksDbRunner runner = new RocksDbRunner(dbName, key, value);
            new Thread(runner).start();
        }
    }

    @Test
    public void testSameDbRunner() {
        String dbName = "test_db_same";
        for (int i=0; i<10; i++) {
            String key = "key_" + i;
            String value = "value_" + i;
            RocksDbRunner runner = new RocksDbRunner(dbName, key, value);
            new Thread(runner).start();
        }
    }

    @Test
    public void test() {
        String value = "小明";
        simpleRocksDB.put("a", value);
        String a = simpleRocksDB.get("a");
        Assert.assertEquals(a, value);
        StringBuilder builder = new StringBuilder();
        boolean exits = simpleRocksDB.exits("a", builder);
        Assert.assertTrue(exits);
    }

    @Test
    public void test2() {
        simpleRocksDB = new SimpleRocksDB(dbName, "rocksDbTest");
        BigDecimal bigDecimal = BigDecimal.valueOf(20.12);
        simpleRocksDB.put("b", bigDecimal);
        BigDecimal value = simpleRocksDB.get("b");
        Assert.assertEquals(value, bigDecimal);
        simpleRocksDB.delete("b");
        BigDecimal value2 = simpleRocksDB.get("b");
        Assert.assertNull(value2);
    }

    @Test
    public void test3() {
        simpleRocksDB.close();
        thrown.expect(SimpleRocksDBException.class);
        thrown.expectMessage("没有找到对应的数据库");
        simpleRocksDB.put("c", "hello world");
    }

}