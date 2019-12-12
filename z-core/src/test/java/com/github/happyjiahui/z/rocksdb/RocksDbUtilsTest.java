package com.github.happyjiahui.z.rocksdb;

import com.github.happyjiahui.z.util.RocksDbUtils;
import org.junit.Assert;
import org.junit.Test;
import org.rocksdb.RocksDB;

public class RocksDbUtilsTest {

    @Test
    public void test() {
        String value = "小明";
        RocksDB rocksDB = RocksDbUtils.init("rocksDbTest");
        RocksDbUtils.put(rocksDB, "a", value);
        String a = RocksDbUtils.get(rocksDB, "a");
        RocksDbUtils.close(rocksDB);
        Assert.assertEquals(a, value);
    }

}