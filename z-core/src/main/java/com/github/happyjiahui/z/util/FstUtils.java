package com.github.happyjiahui.z.util;

import java.io.Serializable;

import org.nustaq.serialization.FSTConfiguration;

/**
 * java序列化工具，比java默认序列化速度高达10倍，100% JDK序列化兼容(好吧，可能是99% ..)。
 * 
 * @author zhaojinbing
 * @version 2019/8/13 8:54
 * @since 0.1
 */
public class FstUtils {

    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    private FstUtils() {

    }

    /**
     * 序列化
     * 
     * @param t
     *            待序列化的对象
     * @param <T>
     *            待序列化的对象的类型
     * @return 序列化后的byte数组
     */
    public static <T extends Serializable> byte[] serializer(T t) {
        return conf.asByteArray(t);
    }

    /**
     * 反序列化
     * 
     * @param bytes
     *            序列化后的byte数组
     * @param <T>
     *            反序列化对象类型
     * @return 反序列化对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserializer(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        Object o = conf.asObject(bytes);
        if (o == null) {
            return null;
        }
        return (T)o;
    }
}
