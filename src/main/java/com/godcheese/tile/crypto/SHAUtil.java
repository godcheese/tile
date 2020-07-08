package com.godcheese.tile.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class SHAUtil {

    /**
     * 字符串 SHA 加密
     *
     * @param plainText 明文
     * @return String
     */
    public static String encrypt( final EncryptType encryptType, final String plainText) {
        if (EncryptType.SHA256.equals(encryptType)) {
            return sha(plainText, encryptType.value);
        }
        if (EncryptType.SHA512.equals(encryptType)) {
            return sha(plainText, encryptType.value);
        }
        return null;
    }

    private static String sha(String sha, String plainText) {
        // 返回值
        String strResult = null;
        // 是否是有效字符串
        if (plainText != null && plainText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并传入加密类型
                MessageDigest messageDigest = MessageDigest.getInstance(sha);
                // 传入要加密的字符串
                messageDigest.update(sha.getBytes());

                // 得到 byte 类型结果
                byte[] byteBuffer = messageDigest.digest();

                // 将 byte 转换为 string
                StringBuilder strHexString = new StringBuilder();
                // 遍历 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回结果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    public enum EncryptType {

        /**
         * SHA512 单向散列算法
         */
        SHA512("SHA-512"),

        /**
         * SHA256 单向散列算法
         */
        SHA256("SHA-256"),

        /**
         * MD5 算法
         */
        MD5("MD5");

        public String value;

        EncryptType(String value) {
            this.value = value;
        }

    }
}
