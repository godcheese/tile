package com.godcheese.tile.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-18
 */
public class ZipUtil {

    private static final int BUFFER = 1024;

    public static final String SUFFIX = ".zip";

    /**
     * 压缩时获取要被压缩的源文件或文件夹下所有的文件或文件夹
     *
     * @param sourceFile 要被压缩的源文件或文件夹
     * @return
     */
    private static List<File> getAllFile(File sourceFile) {
        List<File> resultFileList = new ArrayList<>();
        if (sourceFile.isDirectory()) {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles != null) {
                if (listFiles.length > 0) {
                    for (File file : listFiles) {
                        resultFileList.addAll(getAllFile(file));
                    }
                } else {
                    // 空文件夹直接加入
                    resultFileList.add(sourceFile);
                }
            }
        }
        if (sourceFile.isFile()) {
            resultFileList.add(sourceFile);
        }
        return resultFileList;
    }

    /**
     * 压缩时获取文件或文件夹的相对路径
     *
     * @param sourceFile 要被压缩的源文件或文件夹
     * @param file       要被压缩到 zip 目标文件的文件或文件夹
     * @return String
     */
    private static String getRelativePath(File sourceFile, File file) {
        String relativePath = file.getName();
        while (true) {
            file = file.getParentFile();
            if (file == null) {
                break;
            }
            if (file.equals(sourceFile)) {
                break;
            } else {
                relativePath = file.getName() + File.separator + relativePath;
            }
        }
        return relativePath;
    }

    /**
     * 解压时创建不存在的文件
     *
     * @param targetFile 解压到的目标文件夹
     * @param zipEntry   压缩包里的文件
     * @return File
     */
    private static File createFile(File targetFile, ZipEntry zipEntry) {
        String fileName = zipEntry.getName();
        String[] directories = null;
        if (fileName.contains("\\")) {
            directories = fileName.split("\\\\");
        }
        if (fileName.contains("/")) {
            directories = fileName.split("/");
        }
        if (directories != null && directories.length > 0) {
            // 当文件存在上级目录
            if (directories.length > 1) {
                for (int i = 0; i < (directories.length - 1); i++) {
                    targetFile = new File(targetFile, directories[i]);
                }
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                targetFile = new File(targetFile, directories[directories.length - 1]);
            } else {
                // 当文件不存在上级目录
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                targetFile = new File(targetFile, directories[0]);
            }
        }
        return targetFile;
    }

    /**
     * 压缩文件
     *
     * @param sourceFile    要被压缩的源文件或文件夹
     * @param targetZipFile 被压缩后的 zip 目标文件
     * @return boolean
     */
    public static boolean zip(File sourceFile, File targetZipFile) {
        List<File> fileList = getAllFile(sourceFile);
        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry;
        int readLength = 0;
        FileOutputStream fileOutputStream = null;
        CheckedOutputStream checkedOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            // 对输出文件做 CRC32 校验
            fileOutputStream = new FileOutputStream(targetZipFile);
            checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            zipOutputStream = new ZipOutputStream(checkedOutputStream);

            for (File file : fileList) {
                if (file.isDirectory()) {
                    zipEntry = new ZipEntry(getRelativePath(sourceFile, file) + File.separator);
                    zipOutputStream.putNextEntry(zipEntry);
                }
                if (file.isFile()) {
                    zipEntry = new ZipEntry(getRelativePath(sourceFile, file));
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    while ((readLength = bufferedInputStream.read(buffer, 0, BUFFER)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }
                    bufferedInputStream.close();
                    fileInputStream.close();

                }
            }
            fileList.clear();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (checkedOutputStream != null) {
                try {
                    checkedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 压缩文件，此方法是将所有文件压缩到一个文件夹下
     *
     * @param sourceFiles   要被压缩的源文件或文件夹
     * @param targetZipFile 被压缩后的 zip 目标文件
     * @return
     */
    public static boolean zip(List<File> sourceFiles, File targetZipFile) {
        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry;
        int readLength = 0;
        FileOutputStream fileOutputStream = null;
        CheckedOutputStream checkedOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            // 对输出文件做 CRC32 校验
            fileOutputStream = new FileOutputStream(targetZipFile);
            checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            zipOutputStream = new ZipOutputStream(checkedOutputStream);
            for (File file : sourceFiles) {
                if (file.isDirectory()) {
                    zipEntry = new ZipEntry(file.getName() + File.separator);
                    zipOutputStream.putNextEntry(zipEntry);
                }
                if (file.isFile()) {
                    zipEntry = new ZipEntry(file.getName());
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    while ((readLength = bufferedInputStream.read(buffer, 0, BUFFER)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }
                    bufferedInputStream.close();
                    fileInputStream.close();

                }
            }
            sourceFiles.clear();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (checkedOutputStream != null) {
                try {
                    checkedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 解压 zip 压缩包
     *
     * @param sourceZipFile 要被解压的源 zip 压缩包文件
     * @param targetFile    解压到的目标文件夹
     * @return boolean
     */
    public static boolean unzip(File sourceZipFile, File targetFile) {
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        ZipEntry zipEntry = null;
        OutputStream outputStream = null;
        byte[] buffer = new byte[BUFFER];
        int readLength = 0;
        try {
            fileInputStream = new FileInputStream(sourceZipFile);
            zipInputStream = new ZipInputStream(fileInputStream);
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                System.out.println(zipEntry.getName());
                System.out.println(zipEntry.isDirectory());
                if (zipEntry.isDirectory()) {
                    File directory = new File(targetFile.getCanonicalPath() + File.separator + zipEntry.getName());
                    if (!directory.exists()) {
                        directory.mkdirs();
                        continue;
                    }
                }
                File file = createFile(targetFile, zipEntry);
                outputStream = new FileOutputStream(file);
                while ((readLength = zipInputStream.read(buffer, 0, BUFFER)) != -1) {
                    outputStream.write(buffer, 0, readLength);
                }
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
