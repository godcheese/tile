package com.godcheese.tile.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class Base64Util {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final Base64Delegate BASE_64_DELEGATE;

    static {

        Base64Delegate base64DelegateToUse = null;

        // JDK 8's java.util.Base64 class present?
        base64DelegateToUse = new JdkBase64Delegate();

        BASE_64_DELEGATE = base64DelegateToUse;
    }

    public static String encode(byte[] src) {
        return BASE_64_DELEGATE.encode(src);
    }

    public static byte[] decode(byte[] src) {
        return BASE_64_DELEGATE.decode(src);
    }

    public static byte[] decode(String src) {
        return BASE_64_DELEGATE.decode(src);
    }

    public static byte[] encodeUrlSafe(byte[] src) {
        return BASE_64_DELEGATE.encodeUrlSafe(src);
    }

    public static byte[] decodeUrlSafe(byte[] src) {
        return BASE_64_DELEGATE.decodeUrlSafe(src);
    }

    interface Base64Delegate {

        String encode(byte[] src);

        byte[] decode(byte[] src);

        byte[] decode(String src);

        byte[] encodeUrlSafe(byte[] src);

        byte[] decodeUrlSafe(byte[] src);

    }

    static class JdkBase64Delegate implements Base64Delegate {

        @Override
        public String encode(byte[] src) {
            if (src == null || src.length == 0) {
                return new String(src);
            }
            return new String(Base64.getEncoder().encode(src));
        }

        @Override
        public byte[] decode(byte[] src) {
            if (src == null || src.length == 0) {
                return src;
            }
            return Base64.getDecoder().decode(src);
        }

        @Override
        public byte[] decode(String src) {
            return Base64.getDecoder().decode(src.getBytes());
        }

        @Override
        public byte[] encodeUrlSafe(byte[] src) {
            if (src == null || src.length == 0) {
                return src;
            }
            return Base64.getUrlEncoder().encode(src);
        }

        @Override
        public byte[] decodeUrlSafe(byte[] src) {
            if (src == null || src.length == 0) {
                return src;
            }
            return Base64.getUrlDecoder().decode(src);
        }
    }

}
