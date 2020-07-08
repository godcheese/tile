package com.godcheese.tile.crypto;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-03-07
 */
public class BCryptEncoderUtil {

    public static String encode(String plainText, String salt) {
        return BCrypt.hashpw(plainText, salt);
    }

    public static String salt() {
        return BCrypt.gensalt();
    }

    public static String salt(int strength) {
        return BCrypt.gensalt(strength);
    }

    public static String encode(String plainText) {
        return BCrypt.hashpw(plainText, salt());
    }

    public static boolean matches(String plainText, String cipherText) {
        return BCrypt.checkpw(plainText, cipherText);
    }

    public static String salt(int strength, SecureRandom secureRandom) {
        return BCrypt.gensalt(strength, secureRandom);
    }
}
