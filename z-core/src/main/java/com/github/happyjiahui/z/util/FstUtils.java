package com.github.happyjiahui.z.util;

import java.io.Serializable;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author zhaojinbing
 * @version 2019/8/13 8:54 序列化工具
 */
public class FstUtils {

    private static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

    private FstUtils() {

    }

    public static <T extends Serializable> byte[] serializer(T t) {
        return configuration.asByteArray(t);
    }

    public static <T extends Serializable> T deserializer(byte[] bytes) {
        return (T)configuration.asObject(bytes);
    }
}
