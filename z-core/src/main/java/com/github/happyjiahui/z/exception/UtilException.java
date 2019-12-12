/**
 * Copyright (c) [2019] [name of copyright holder] [Software Name] is licensed under the Mulan PSL v1. You can use this
 * software according to the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.exception;

/**
 * 业务异常
 * 
 * @author zhaojinbing
 */
public class UtilException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UtilException() {
        super();
    }

    public UtilException(String s) {
        super(s);
    }

    public UtilException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UtilException(Throwable throwable) {
        super(throwable);
    }
}