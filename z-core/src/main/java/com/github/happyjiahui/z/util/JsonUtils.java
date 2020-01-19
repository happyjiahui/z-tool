/**
 * Copyright (c) 2019 beyondjinbing z-tool is licensed under the Mulan PSL v1. You can use this software according to
 * the terms and conditions of the Mulan PSL v1. You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE. See the Mulan PSL v1 for more details.
 */

package com.github.happyjiahui.z.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.github.happyjiahui.z.exception.UtilException;

/**
 * @author beyondjinbing
 * @since 0.1
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        DateFormat format = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        mapper.setDateFormat(format);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 忽略未知字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // null转为空字符串
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
                jsonGenerator.writeString("");
            }
        });
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    private JsonUtils() {

    }

    /**
     * 将对象序列化为json字符串
     * 
     * @param t
     *            对象
     * @param <T>
     *            对象类型
     * @return json字符串
     */
    public static <T> String toString(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

    /**
     * 将json字符串反序列化为java对象
     * 
     * @param jsonStr
     *            json字符串
     * @param tClass
     *            java对象
     * @param <T>
     *            java对象类型
     * @return 反序列化后的java对象
     */
    public static <T> T parseObj(String jsonStr, Class<T> tClass) {
        try {
            return mapper.readValue(jsonStr, tClass);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

    /**
     * 将json字符串反序列化为list
     * 
     * @param jsonStr
     *            json字符串
     * @param tClass
     *            java对象
     * @param <T>
     *            java对象类型
     * @return 反序列化后的java对象
     */
    public static <T> List<T> parseList(String jsonStr, Class<T> tClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, tClass);
        try {
            return mapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

    /**
     * 将json字符串反序列化为Set
     * 
     * @param jsonStr
     *            json字符串
     * @param tClass
     *            java对象
     * @param <T>
     *            java对象类型
     * @return 反序列化后的java对象
     */
    public static <T> Set<T> parseSet(String jsonStr, Class<T> tClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(Set.class, tClass);
        try {
            return mapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

    /**
     * 将json字符串反序列化为Map
     * 
     * @param jsonStr
     *            json字符串
     * @param keyClass
     *            key类型
     * @param valueClass
     *            value类型
     * @param <K>
     *            key对象类型
     * @param <V>
     *            value对象类型
     * @return 反序列化后的java对象
     */
    public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        try {
            return mapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

    /**
     * 自定义反序列化
     * 
     * @param jsonStr
     *            json字符串
     * @param typeReference
     *            TypeReference
     * @param <T>
     *            反序列化后的java对象类型
     * @return 反序列化后的java对象
     */
    public static <T> T parseObj(String jsonStr, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException e) {
            throw new UtilException(e.getMessage(), e);
        }
    }

}
