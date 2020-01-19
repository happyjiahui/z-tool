/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.rocksDb;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.github.happyjiahui.z.util.FileUtils;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.happyjiahui.z.util.FstUtils;

/**
 * rocksDB 简单封装，提供易用的rocksDB常用方法
 * 
 * @author zhaojinbing
 */
public class SimpleRocksDB {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRocksDB.class);
    private static final ConcurrentHashMap<String, RocksDB> ROCKS_DB_MAP = new ConcurrentHashMap<>();

    private String dbName;

    public SimpleRocksDB(String dbName, String dbPath) {
        this.dbName = dbName;
        init(dbName, dbPath, null);
    }

    public SimpleRocksDB(String dbName, String dbPath, Options options) {
        this.dbName = dbName;
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
        if (ROCKS_DB_MAP.containsKey(dbName)) {
            LOGGER.info("数据库{}已经存在，直接使用。", dbName);
            return;
        }
        synchronized (SimpleRocksDB.class) {
            if (!ROCKS_DB_MAP.containsKey(dbName)) {
                RocksDB.loadLibrary();
                if (options == null) {
                    options = getDefaultOptions();
                }
                try {
                    String path = dbPath + File.separator + dbName;
                    FileUtils.mkdirIfNoExists(path);
                    RocksDB rocksDB = RocksDB.open(options, path);
                    ROCKS_DB_MAP.putIfAbsent(dbName, rocksDB);
                    LOGGER.info("数据库{}初始化完成。", dbName);
                } catch (RocksDBException e) {
                    throw new SimpleRocksDBException("rocksDb 初始化失败", e);
                }
            }
        }
    }

    /**
     * 获取数据库实例
     * 
     * @param dbName
     *            数据库名称
     * @return 数据库实例 {@link RocksDB}
     */
    private RocksDB getRocksDB(String dbName) {
        RocksDB rocksDB = ROCKS_DB_MAP.get(dbName);
        if (rocksDB == null) {
            throw new SimpleRocksDBException("没有找到对应的数据库");
        }
        return rocksDB;
    }

    /**
     * 从数据库中放入值
     *
     * @param key
     *            key值
     * @param value
     *            value
     * @param <T>
     *            value类型
     */
    public <T extends Serializable> void put(String key, T value) {
        RocksDB rocksDB = getRocksDB(this.dbName);
        byte[] keyBytes = FstUtils.serializer(key);
        byte[] valueBytes = FstUtils.serializer(value);

        try {
            rocksDB.put(keyBytes, valueBytes);
        } catch (RocksDBException e) {
            throw new SimpleRocksDBException("存入rocksDb失败", e);
        }
    }

    /**
     * 从数据库中取值
     *
     * @param key
     *            key值
     * @param <T>
     *            value类型
     * @return value
     */
    public <T extends Serializable> T get(String key) {
        RocksDB rocksDB = getRocksDB(this.dbName);

        byte[] keyBytes = FstUtils.serializer(key);
        try {
            byte[] bytes = rocksDB.get(keyBytes);
            return FstUtils.deserializer(bytes);
        } catch (RocksDBException e) {
            throw new SimpleRocksDBException("从rocksDb中取值失败", e);
        }
    }

    /**
     * 从数据库中删除值
     *
     * @param key
     *            key值
     */
    public void delete(String key) {
        RocksDB rocksDB = getRocksDB(this.dbName);

        byte[] keyBytes = FstUtils.serializer(key);
        try {
            rocksDB.delete(keyBytes);
        } catch (RocksDBException e) {
            throw new SimpleRocksDBException("从rocksDb中删除值失败", e);
        }
    }

    /**
     * 关闭数据库
     *
     */
    public void close() {
        RocksDB rocksDB = getRocksDB(this.dbName);
        rocksDB.close();
        ROCKS_DB_MAP.remove(this.dbName);
    }

    /**
     * 判断key是否存在
     * 
     * @param key
     *            key值
     * @param value
     *            StringBuilder instance which is a out parameter if a value is found in block-cache.
     * @return boolean value indicating if key does not exist or might exist.
     */
    public boolean exits(String key, StringBuilder value) {
        RocksDB rocksDb = getRocksDB(this.dbName);
        byte[] keyBytes = FstUtils.serializer(key);
        return rocksDb.keyMayExist(keyBytes, value);
    }

}
