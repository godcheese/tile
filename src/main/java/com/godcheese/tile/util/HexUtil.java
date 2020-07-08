package com.godcheese.tile.util;

/**
 * 十六进制
 *
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-06-03
 */
public class HexUtil {

    /**
     * 二进制转换成十六进制
     *
     * @param bytes 二进制
     * @return
     */
    public static String parseByteToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuilder.append(hex.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 十六制转换成二进制
     *
     * @param hexString 十六进制字符串
     * @return
     */
    public static byte[] parseHexStringToByte(String hexString) {
        if (hexString.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
