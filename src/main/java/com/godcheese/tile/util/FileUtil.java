package com.godcheese.tile.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class FileUtil {

    private FileUtil() {
    }

    /**
     * 文件下载
     *
     * @param httpServletResponse HttpServletResponse
     * @param filename            文件名，如：filename.doc
     * @param absoluteFile        欲下载的原始文件
     * @throws IOException
     */
    public static void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String filename, File absoluteFile) throws IOException {

        // 设置头
        httpServletResponse.setHeader("Cache-Control", "no-cache");

        //设置日期头
        httpServletResponse.setDateHeader("Expires", 0);

        // 设置文件流
        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");

        filename = avoidChineseMessyCode(httpServletRequest, filename);

        // 设置下载文件内容长度
        httpServletResponse.setContentLengthLong(absoluteFile.length());

        // 设置文件名
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

        byte[] bytes = new byte[1024];
        int len;
        try (FileInputStream fileInputStream = new FileInputStream(absoluteFile);
             OutputStream outputStream = httpServletResponse.getOutputStream()) {
            while ((len = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            // 成功下载文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 文件下载
     *
     * @param httpServletResponse HttpServletResponse
     * @param filename            文件名，如：filename.doc
     * @param absoluteFile        欲下载的原始文件
     * @throws IOException
     */
    public static void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String contentType, String filename, File absoluteFile) throws IOException {

        // 设置头
        httpServletResponse.setHeader("Cache-Control", "no-cache");

        //设置日期头
        httpServletResponse.setDateHeader("Expires", 0);

        // 设置文件流
        httpServletResponse.setContentType(contentType);

        filename = avoidChineseMessyCode(httpServletRequest, filename);

        // 设置下载文件内容长度
        httpServletResponse.setContentLengthLong(absoluteFile.length());

        // 设置文件名
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

        byte[] bytes = new byte[1024];
        int len;
        try (FileInputStream fileInputStream = new FileInputStream(absoluteFile);
             OutputStream outputStream = httpServletResponse.getOutputStream()) {
            while ((len = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            // 成功下载文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 避免中文字符乱码
     *
     * @param httpServletRequest
     * @param filename
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String avoidChineseMessyCode(HttpServletRequest httpServletRequest, String filename) throws UnsupportedEncodingException {
        // 为防止中文文件名的乱码显示
        //filename =new String(filename.getBytes("ISO8859-1"),"UTF-8");
        String agent = httpServletRequest.getHeader("User-Agent");
//        if (agent != null && agent.toLowerCase().contains(ClientUtil.Browser.FIREFOX.toLowerCase())) {
//            // 针对火狐浏览器处理方式不一样了
//        } else {
//        }
//

        if (agent != null && agent.toLowerCase().contains("msie")) {
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
        } else {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        // 编码后文件名中的空格被替换为“+”号，所以此处将替换编码后文件名中“+”号为UTF-8中空格的“%20”编码
        filename = filename.replaceAll("\\+", "%20");

        return filename;
    }

    /**
     * 获取原始文件名的扩展名
     *
     * @param originalFilename 文件原始文件名
     * @return String
     */
    public static String getOriginalFilenameExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    /**
     * @param file 欲删除的文件
     * @return Boolean
     */
    public static Boolean delete(File file) {
        return file.delete();
    }


//    /**
//     * 删除文件夹或文件
//     *
//     * @param directoryOrFilename
//     * @return
//     */
//    public static boolean delete(String directoryOrFilename) {
//        File file = new File(fullDirectoryOrFilename(directoryOrFilename));
//        return !file.exists() || file.delete();
//    }
//
//    public static boolean isAllowedExtension(String fileExtension, String extensions) {
//
//        if (extensions == null) {
//            return false;
//        }
//        extensions = extensions.trim();
//
//        if (extensions.equals("")) {
//            return false;
//        }
//
//        if (extensions.equals("*")) {
//            return true;
//        }
//
//        int commaLastIndex = extensions.lastIndexOf(",");
//
//        if (commaLastIndex == extensions.length() - 1) {
//            extensions = extensions.substring(0, commaLastIndex);
//            String[] extensionArray = extensions.split(",");
//            List<String> list = Arrays.asList(extensionArray);
//            if (list.contains(fileExtension)) {
//                return true;
//            }
//        }
//
//
//        // file.upload.extension 为空时则允许所s有文件上传
//        return commaLastIndex == 0 || fileExtension.equals(extensions);
//    }

    /**
     * 获取指定文件夹或文件的全路径（物理）
     *
     * @param directoryOrFilename
     * @return
     */
//    public static String fullDirectoryOrFilename(String directoryOrFilename) {
//        return filterFileSeparator(directoryOrFilename);
//    }

    /**
     * 过滤 / \ 等文件路径分隔符为系统默认分隔符
     *
     * @param path
     * @return
     */
    public static String filterFileSeparator(String path) {

        String temp = null;
        if (path != null) {
            path = path.trim();
            path = "".equals(path) ? "/" : path;

            // 兼容 spring-boot:run 部署及Tomcat下war部署

            // windows \ ; linux /  全部统一替换为 /
            String separator = File.separator;

            // windows 下将 \ 转成\\，不然报错：java.lang.IllegalArgumentException: character to be escaped is missing
            // windows 下regex应该这样转 \\ => \\\\

            String prefix1 = "/";
            String prefix2 = "\\";
            String prefix2regex = "\\\\";
            separator = separator.equals(prefix2) ? prefix2regex : separator;

            while (prefix1.equals(path.substring(0, 1))) {
                path = path.substring(1, path.length());
            }

            while (prefix2.equals(path.substring(0, 1))) {
                path = path.substring(1, path.length());
            }

            if (path.contains(prefix1)) {
                path = path.replaceAll(prefix1, separator);
            }

            if (path.contains(prefix2)) {
                path = path.replaceAll(prefix2regex, separator);
            }
            temp = path;
        }

        return temp;
    }

    /**
     * 创建文件夹，存在即不新建
     *
     * @param directoryPath
     * @return
     */
    public static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() || directory.mkdirs();
    }

    /**
     * 强制创建文件夹，存在则删除新建
     *
     * @param directoryPath
     * @return
     */
    public static boolean createDirectoryForce(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            directory.delete();
        }
        return directory.mkdirs();
    }

    /**
     * 获取项目当前运行根目录
     *
     * @return
     */
    public static String getCurrentRootPath() {
        String rootPath = null;
        try {
            // 兼容 Tomcat 部署时获取当前运行根目录
            String nodePath = FileUtil.class.getClassLoader().getResource("/").getPath();
            rootPath = nodePath.substring(1, nodePath.length() - 16);
        } catch (NullPointerException nullPointerException) {
            try {
                rootPath = new File("").getCanonicalPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rootPath;
    }

    public static boolean exists(File file) {
        return file.exists();
    }

    public static void copyFile(File sourceFile, File targetFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(sourceFile);
            fileOutputStream = new FileOutputStream(targetFile);
            copyFile(fileInputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(FileInputStream sourceFileInputStream, FileOutputStream targetFileOutputStream) {

        try {

            int len;
            byte[] bytes = new byte[1024];
            while ((len = sourceFileInputStream.read(bytes)) > 0) {
                targetFileOutputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getName(String filename) {
        String name = getNameWithSuffix(filename);
        return name.substring(0, name.lastIndexOf("."));
    }

    public static String getNameWithSuffix(String filename) {
        String separator1 = "\\";
        String separator2 = "/";
        String name = null;
        if (!"".equals(filename.trim())) {
            if (filename.contains(separator1) || filename.contains(separator2)) {
                filename = filterFileSeparator(filename);
            }
            name = filename.substring(filename.lastIndexOf(File.separator) + 1);
        }
        return name;
    }

    public static String getSuffix(String filename) {
        String suffix = null;
        if (filename != null) {
            if (!"".equals(filename.trim()) && filename.indexOf(".") > 0) {
                suffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
            }
        }
        return suffix;
    }

    public static boolean isAllowedSuffix(String fileSuffix, String suffixes) {
        if (suffixes == null) {
            return false;
        }
        suffixes = suffixes.trim();
        if ("".equals(suffixes)) {
            return false;
        }
        if ("*".equals(suffixes)) {
            return true;
        }
        int commaLastIndex = suffixes.lastIndexOf(",");

        if (commaLastIndex == suffixes.length() - 1) {
            suffixes = suffixes.substring(0, commaLastIndex);
            String[] extensionArray = suffixes.split(",");
            List<String> list = Arrays.asList(extensionArray);
            if (list.contains(fileSuffix)) {
                return true;
            }
        }

        // file.upload.extension 为空时则允许所s有文件上传
        return commaLastIndex == 0 || fileSuffix.equals(suffixes);
    }
}
