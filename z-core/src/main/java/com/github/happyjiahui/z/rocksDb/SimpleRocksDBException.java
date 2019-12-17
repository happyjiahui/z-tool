/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.rocksDb;

/**
 * rocksDB异常
 * 
 * @author zhaojinbing
 */
public class SimpleRocksDBException extends RuntimeException {
    public SimpleRocksDBException() {
        super();
    }

    public SimpleRocksDBException(String message) {
        super(message);
    }

    public SimpleRocksDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleRocksDBException(Throwable cause) {
        super(cause);
    }
}
