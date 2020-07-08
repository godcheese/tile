package com.godcheese.tile.util;

import java.io.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-06-03
 */
public class ByteArrayUtil {

    private static final int BUFFER_SIZE = 1024;

    /**
     * 文件转成二进制数组
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] fileToByteArray(File file) throws Exception {
        byte[] data = new byte[0];
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        byte[] cache = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = fileInputStream.read(cache)) != -1) {
            byteArrayOutputStream.write(cache, 0, read);
            byteArrayOutputStream.flush();
        }
        byteArrayOutputStream.close();
        fileInputStream.close();
        data = byteArrayOutputStream.toByteArray();
        return data;
    }

    /**
     * 二进制数组转成文件
     *
     * @param bytes
     * @param file
     * @throws Exception
     */
    public static void byteArrayToFile(byte[] bytes, File file) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] cache = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = inputStream.read(cache)) != -1) {
            outputStream.write(cache, 0, read);
            outputStream.flush();
        }
        outputStream.close();
        inputStream.close();
    }
}
