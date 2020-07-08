package com.godcheese.tile.crypto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-06-01
 */
public class AESUtil {

    private static final String ALGORITHM_NAME = "AES";

    private static final int KEY_SIZE = 128;

    private static final int BUFFER_SIZE = 1024;

    /**
     * 获取 secret key，避免出现 AES 加密解密时必须要求 16 个字节的问题
     *
     * @param secretKeySeed 密钥种子
     * @return
     * @throws Exception
     */
    public static String getSecretKey(String secretKeySeed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
        SecureRandom secureRandom = secretKeySeed != null && !"".equals(secretKeySeed) ? new SecureRandom(secretKeySeed.getBytes()) : new SecureRandom();
        keyGenerator.init(KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encode(secretKey.getEncoded());
    }

    /**
     * 加密
     *
     * @param secretKey 密钥
     * @param plainText 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(String secretKey, String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Key key = new SecretKeySpec(Base64Util.decode(secretKey), ALGORITHM_NAME);
        byte[] encodeFormat = key.getEncoded();
        SecretKey secretKeySpec = new SecretKeySpec(encodeFormat, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * 解密
     *
     * @param secretKey  密钥
     * @param cipherText 密文
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt(String secretKey, byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, IOException {
        Key key = new SecretKeySpec(Base64Util.decode(secretKey), ALGORITHM_NAME);
        byte[] encodeFormat = key.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodeFormat, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(cipherText));
    }

    /**
     * 加密文件
     *
     * @param secretKey  密钥
     * @param sourceFile 要加密的文件
     * @param targetFile 加密后保存的文件
     * @throws Exception
     */
    public static void encryptFile(String secretKey, File sourceFile, File targetFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        Key key = new SecretKeySpec(Base64Util.decode(secretKey), ALGORITHM_NAME);
        byte[] encodeFormat = key.getEncoded();
        SecretKey secretKeySpec = new SecretKeySpec(encodeFormat, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
        byte[] buffer = new byte[BUFFER_SIZE];
        int readLength = 0;
        while ((readLength = cipherInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, readLength);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        cipherInputStream.close();
        fileInputStream.close();
    }

    /**
     * 解密文件
     *
     * @param secretKey  密钥
     * @param sourceFile 要解密的文件
     * @param targetFile 解密后保存的文件
     * @throws Exception
     */
    public static void decryptFile(String secretKey, File sourceFile, File targetFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        Key key = new SecretKeySpec(Base64Util.decode(secretKey), ALGORITHM_NAME);
        byte[] encodeFormat = key.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodeFormat, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
        byte[] buffer = new byte[BUFFER_SIZE];
        int readLength = 0;
        while ((readLength = fileInputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, readLength);
            cipherOutputStream.flush();
        }
        cipherOutputStream.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
