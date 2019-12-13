/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.rocksDb;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.happyjiahui.z.exception.UtilException;
import com.github.happyjiahui.z.util.FstUtils;

public class RocksDbHelp {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDbHelp.class);
    private static final ConcurrentHashMap<String, RocksDB> ROCKS_DB_MAP = new ConcurrentHashMap<>();

    public RocksDbHelp(String dbName, String dbPath) {
        init(dbName, dbPath, null);
    }

    public RocksDbHelp(String dbName, String dbPath, Options options) {
        init(dbName, dbPath, options);
    }

    private Options getDefaultOptions() {
        Options options = new Options();
        options.setNewTableReaderForCompactionInputs(true);
        options.setCreateIfMissing(true);
        // 日志级别
        options.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
        // 增大max_background_flushes,这样就能有更多的线程同时flush memtable
        options.setMaxBackgroundFlushes(500);
        // 增大max_background_compactions,用更多的线程来进行compaction
        options.setMaxBackgroundCompactions(500);

        options.setMaxFileOpeningThreads(100);
        options.setMaxTotalWalSize(1024 * 1024 * 100);
        options.setDbWriteBufferSize(1024 * 1024 * 100);
        options.setMaxLogFileSize(1024 * 1024 * 100);
        options.setMaxOpenFiles(500);

        options.setSoftPendingCompactionBytesLimit(1024 * 1024 * 1024);
        options.setHardPendingCompactionBytesLimit(1024 * 1024 * 1024);
        return options;
    }

    /**
     * 初始化rocksDb
     *
     * @param dbName
     *            数据库名称
     * @param dbPath
     *            数据库路径
     * @param options
     *            rocksDb配置 {@link Options}
     */
    private void init(String dbName, String dbPath, Options options) {
        boolean isExits = ROCKS_DB_MAP.containsKey(dbName);
        if (isExits) {
            LOGGER.info("数据库{}已经存在，直接使用。", dbName);
            return;
        }
        RocksDB.loadLibrary();
        if (options == null) {
            options = getDefaultOptions();
        }
        try {
            RocksDB rocksDB = RocksDB.open(options, dbPath);
            ROCKS_DB_MAP.putIfAbsent(dbName, rocksDB);
            LOGGER.info("数据库{}初始化完成。", dbName);
        } catch (RocksDBException e) {
            throw new UtilException("rocksDb 初始化失败");
        }
    }

    /**
     * 获取数据库实例
     * 
     * @param dbName
     *            数据库名称
     * @return 数据库实例 {@link RocksDB}
     */
    private RocksDB getRocksDb(String dbName) {
        RocksDB rocksDB = ROCKS_DB_MAP.get(dbName);
        if (rocksDB == null) {
            throw new UtilException("没有找到对应的数据库");
        }
        return rocksDB;
    }

    /**
     * 从数据库中放入值
     *
     * @param dbName
     *            数据库名称
     * @param key
     *            key值
     * @param value
     *            value
     * @param <T>
     *            value类型
     */
    public <T extends Serializable> void put(String dbName, String key, T value) {
        RocksDB rocksDB = getRocksDb(dbName);
        byte[] keyBytes = FstUtils.serializer(key);
        byte[] valueBytes = FstUtils.serializer(value);

        try {
            rocksDB.put(keyBytes, valueBytes);
        } catch (RocksDBException e) {
            throw new UtilException("存入rocksDb失败", e);
        }
    }

    /**
     * 从数据库中取值
     *
     * @param dbName
     *            数据库名称
     * @param key
     *            key值
     * @param <T>
     *            value类型
     * @return value
     */
    public <T extends Serializable> T get(String dbName, String key) {
        RocksDB rocksDB = getRocksDb(dbName);

        byte[] keyBytes = FstUtils.serializer(key);
        try {
            byte[] bytes = rocksDB.get(keyBytes);
            return FstUtils.deserializer(bytes);
        } catch (RocksDBException e) {
            throw new UtilException("从rocksDb中取值失败", e);
        }
    }

    /**
     * 从数据库中删除值
     *
     * @param dbName
     *            数据库名称
     * @param key
     *            key值
     */
    public void delete(String dbName, String key) {
        RocksDB rocksDB = getRocksDb(dbName);

        byte[] keyBytes = FstUtils.serializer(key);
        try {
            rocksDB.delete(keyBytes);
        } catch (RocksDBException e) {
            throw new UtilException("从rocksDb中删除值失败", e);
        }
    }

    /**
     * 关闭数据库
     *
     * @param dbName
     *            数据库名称
     */
    public void close(String dbName) {
        RocksDB rocksDB = getRocksDb(dbName);
        rocksDB.close();
        ROCKS_DB_MAP.remove(dbName);
    }

}
