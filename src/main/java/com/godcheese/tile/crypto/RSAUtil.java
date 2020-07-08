package com.godcheese.tile.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class RSAUtil {

    private static final String ALGORITHM_NAME = "RSA";
    private static final Integer KEY_SIZE = 1024;
    private static final KeyPair KEY_PAIR;

    /**
     * RSA 最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA 最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final String CIPHER_INSTANCE_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    static {
        KeyPair keyPairToUse = null;
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
            keyPairGenerator.initialize(KEY_SIZE);
            keyPairToUse = keyPairGenerator.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KEY_PAIR = keyPairToUse;
    }

    /**
     * 获取一对非对称加密密钥
     *
     * @return
     */
    public static Map<RSA_KEY, String> getKeys() {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KEY_PAIR.getPrivate();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KEY_PAIR.getPublic();
        Map<RSA_KEY, String> map = new HashMap<>(2);
        map.put(RSA_KEY.PUBLIC_KEY, Base64Util.encode(rsaPublicKey.getEncoded()));
        map.put(RSA_KEY.PRIVATE_KEY, Base64Util.encode(rsaPrivateKey.getEncoded()));
        return map;
    }

    /**
     * 获取一对非对称加密密钥中的 Public key 或 Private key
     *
     * @param rsaKey
     * @return
     */
    public static String getKey(Map<RSA_KEY, String> keys, RSA_KEY rsaKey) {
        return getKeys().get(rsaKey);
    }

    /**
     * 用 Public key （公钥）加密明文
     *
     * @param publicKey
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String encryptByPublicKey(String publicKey, String plainText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] plainTextBytes = plainText.getBytes();
        byte[] publicKeyBytes = Base64Util.decode(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        // Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedTextBytes = cipherByBlock(cipher, plainTextBytes, 0, MAX_DECRYPT_BLOCK);
        return Base64Util.encode(encryptedTextBytes);
    }

    /**
     * 用 Private key （私钥）解密密文
     *
     * @param privateKey
     * @param cipherText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String decryptByPrivateKey(String privateKey, String cipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] cipherTextBytes = Base64Util.decode(cipherText);
        byte[] privateKeyBytes = Base64Util.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptTextBytes = cipherByBlock(cipher, cipherTextBytes, 0, MAX_DECRYPT_BLOCK);
        return new String(decryptTextBytes);
    }

    /**
     * 对数据分段加密/解密
     *
     * @param cipher
     * @param textBytes
     * @param offset
     * @param maxBlockSize
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] cipherByBlock(Cipher cipher, byte[] textBytes, int offset, int maxBlockSize) throws BadPaddingException, IllegalBlockSizeException {
        int i = 0;
        byte[] cache;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int textBytesLength = textBytes.length;
        while (textBytesLength - offset > 0) {
            if (textBytesLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(textBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(textBytes, offset, textBytesLength - offset);
            }
            byteArrayOutputStream.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

    public enum RSA_KEY {

        // public key
        PUBLIC_KEY,

        // private key
        PRIVATE_KEY
    }
}
