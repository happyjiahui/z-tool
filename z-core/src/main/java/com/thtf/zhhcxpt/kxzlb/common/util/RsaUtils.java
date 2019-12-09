package com.thtf.zhhcxpt.kxzlb.common.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thtf.zhhcxpt.kxzlb.common.exception.BusinessException;

/**
 * 非对称加密工具类
 * 
 * @author zhaojinbing
 * @version 2019/9/4 13:40
 */
public class RsaUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtils.class);

    private RsaUtils() {

    }

    /**
     * 生成私钥
     * 
     * @param key
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(apiKeySecretBytes);
        PrivateKey privateKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("生成私钥失败", e);
        }
        return privateKey;
    }

    /**
     * 生成公钥
     * 
     * @param key
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("生成公钥失败", e);
        }
        return publicKey;
    }

    /**
     * 用公钥加密
     * 
     * @param content
     * @param publicKey
     * @return
     */
    private static byte[] encryptByPublicKey(byte[] content, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("用公钥加密失败", e);
        }
    }

    /**
     * 用公钥加密
     * 
     * @param content
     * @param key
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] content, String key) {
        PublicKey publicKey = RsaUtils.getPublicKey(key);
        return RsaUtils.encryptByPublicKey(content, publicKey);
    }

    /**
     * 用私钥解密
     * 
     * @param content
     * @param privateKey
     * @return
     */
    private static byte[] decryptByPrivateKey(byte[] content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("用私钥解密失败", e);
        }
    }

    /**
     * 用私钥解密
     * 
     * @param content
     * @param key
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] content, String key) {
        PrivateKey privateKey = RsaUtils.getPrivateKey(key);
        return RsaUtils.decryptByPrivateKey(content, privateKey);
    }
}
