/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.util;

import java.io.Serializable;

import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import com.github.happyjiahui.z.exception.UtilException;
import com.github.happyjiahui.z.util.FstUtils;

/**
 * @author beyondjinbing
 * @version 0.1.7
 */
public class RocksDbUtils {

    private RocksDbUtils() {

    }

    /**
     * 初始化rocksDb
     * 
     * @param dbPath
     *            db路径
     * @param options
     *            rocksDb配置 {@link Options}
     * @return {@link RocksDB}实例
     */
    public static RocksDB init(String dbPath, Options options) {
        RocksDB.loadLibrary();
        if (options == null) {
            options = getDefaultOptions();
        }
        try {
            return RocksDB.open(options, dbPath);
        } catch (RocksDBException e) {
            throw new UtilException("rocksDb 初始化失败");
        }
    }

    /**
     * 初始化rocksDb
     * 
     * @param dbPath
     *            db路径
     * @return {@link RocksDB}实例
     */
    public static RocksDB init(String dbPath) {
        return init(dbPath, null);
    }

    private static Options getDefaultOptions() {
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
     * 关闭数据库
     * 
     * @param rocksDB
     *            {@link RocksDB}实例
     */
    public static void close(RocksDB rocksDB) {
        rocksDB.close();
    }

    /**
     * 从数据库中放入值
     * 
     * @param rocksDB
     *            {@link RocksDB}实例
     * @param key
     *            key值
     * @param value
     *            value
     * @param <T>
     *            value类型
     */
    public static <T extends Serializable> void put(RocksDB rocksDB, String key, T value) {
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
     * @param rocksDB
     *            {@link RocksDB}实例
     * @param key
     *            key值
     * @param <T>
     *            value类型
     * @return value
     */
    public static <T extends Serializable> T get(RocksDB rocksDB, String key) {
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
     * @param rocksDB
     *            {@link RocksDB}实例
     * @param key
     *            key值
     */
    public static void delete(RocksDB rocksDB, String key) {
        byte[] keyBytes = FstUtils.serializer(key);
        try {
            rocksDB.delete(keyBytes);
        } catch (RocksDBException e) {
            throw new UtilException("从rocksDb中删除值失败", e);
        }
    }

}
