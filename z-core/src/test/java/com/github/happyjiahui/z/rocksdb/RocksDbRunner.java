/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.rocksdb;

import org.junit.Assert;

import com.github.happyjiahui.z.rocksDb.SimpleRocksDB;

public class RocksDbRunner implements Runnable {

    private String dbName;
    private String key;
    private String value;

    public RocksDbRunner(String dbName, String key, String value) {
        this.dbName = dbName;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        SimpleRocksDB simpleRocksDB = new SimpleRocksDB(this.dbName, "/tmp/rocksDbTest");
        simpleRocksDB.put(this.key, this.value);
        String a = simpleRocksDB.get(this.key);
        Assert.assertEquals(a, this.value);
    }
}
