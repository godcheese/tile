package com.godcheese.tile;

import com.godcheese.tile.web.security.jwt.JwtEntity;
import com.godcheese.tile.web.security.jwt.JwtProperties;
import com.godcheese.tile.web.security.jwt.JwtUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class TileApplication {

    public static void main(String[] args) {

        JwtProperties jwtProperties = new JwtProperties();
        JwtEntity jwtEntity = new JwtEntity();
        JwtUtil jwtUtil = JwtUtil.getInstance(jwtProperties);


        String token = jwtUtil.generateToken(jwtEntity);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        map.put("refreshToken", token);
        map.put("issuedAt", jwtEntity.getIssuedAt());
        map.put("issuedAtDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(String.format("token=%s,refreshToken=%s,issuedAt=%s,issuedAtDate=%s", token, token, jwtEntity.getIssuedAt(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        Map<JwtUtil.TokenType, String> map2 = jwtUtil.generateTokenWithRefreshToken(jwtEntity);
        map = new HashMap<>(1);
        map.put("token", map2.get(JwtUtil.TokenType.ACCESS_TOKEN));
        map.put("refreshToken", map2.get(JwtUtil.TokenType.REFRESH_TOKEN));
        map.put("issuedAt", jwtEntity.getIssuedAt());
        map.put("issuedAtDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(String.format("token=%s,refreshToken=%s,issuedAt=%s,issuedAtDate=%s", token, token, jwtEntity.getIssuedAt(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    }


//        byte[] bytes = new byte[0];
//        try {
//            String secretKey = AESUtil.getSecretKey("regerge耳朵如果而个而rg1234");
//            bytes = AESUtil.encrypt(secretKey, "32q33靠打工i个i欧冠金额二哥热吻如果");
//            System.out.println(new String(bytes));
//            String s = AESUtil.decrypt(secretKey, bytes);
//            System.out.println(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        File sourceFile = new File("C:\\Users\\Administrator\\Desktop\\演讲.txt");
//        File targetFile = new File("C:\\Users\\Administrator\\Desktop\\加密后1.txt");
//        try {
//            if (targetFile.exists()) {
//                targetFile.delete();
//            }
//            targetFile.createNewFile();
//            String secretKey = AESUtil.getSecretKey("regerge耳朵如果而个而rg1234");
//            AESUtil.encryptFile(secretKey, sourceFile, targetFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        File sourceFile = new File("C:\\Users\\Administrator\\Desktop\\加密后1.txt");
//        File targetFile = new File("C:\\Users\\Administrator\\Desktop\\解密后1.txt");
//        try {
//            if (targetFile.exists()) {
//                targetFile.delete();
//            }
//            targetFile.createNewFile();
//            String secretKey = AESUtil.getSecretKey("regerge耳朵如果而个而rg1234");
//            AESUtil.decryptFile(secretKey, sourceFile, targetFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
}
