package com.godcheese.tile.web.http;

import com.godcheese.tile.exception.FileNotAllowedException;
import com.godcheese.tile.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-20
 */
public class MultipartFileUtil {

    /**
     * 文件上传
     *
     * @param relativePath       上传指向的目标目录，如：/upload/
     * @param multipartFile      org.springframework.web.multipart.MultipartFile
     * @param allowFileExtension 允许上传的文件的扩展名，如：.doc .ppt
     * @return MultipartFile|null
     * @throws IOException
     * @throws FileNotAllowedException
     */
    public static MultipartFile upload(String relativePath, MultipartFile multipartFile, List<String> allowFileExtension) throws IOException, FileNotAllowedException {
        String extension = getMultipartFileExtension(multipartFile);
        if (!allowFileExtension.isEmpty() && allowFileExtension.contains(extension)) {
            throw new FileNotAllowedException();
        }
        BufferedOutputStream bufferedOutputStream;
        byte[] multipartFileBytes = multipartFile.getBytes();

        UUID uuid = UUID.randomUUID();
        String uuidFilename = uuid + extension;
        // 判断欲上传的目标文件夹是否存在
        if (new File(relativePath).exists()) {
            String tempFile = relativePath + "/" + uuidFilename;
            File file = new File(tempFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(multipartFileBytes);
            bufferedOutputStream.close();
            return multipartFile;
        }
        return null;
    }

    /**
     * 获取 MultipartFile 文件的扩展名
     *
     * @param multipartFile org.springframework.web.multipart.MultipartFile
     * @return String
     */
    public static String getMultipartFileExtension(MultipartFile multipartFile) {
        return FileUtil.getOriginalFilenameExtension(multipartFile.getOriginalFilename());
    }
}
