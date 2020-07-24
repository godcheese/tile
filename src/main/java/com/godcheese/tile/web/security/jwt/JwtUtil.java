package com.godcheese.tile.web.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-28
 */
public class JwtUtil {

    private static final String REFRESH_EXPIRATION_CLAIM_NAME = "rep";
    private static JwtUtil instance;

    private static JwtProperties jwtProperties;

    private JwtUtil() {
    }

    public static synchronized JwtUtil getInstance(JwtProperties properties) {
        jwtProperties = properties;
        if (instance == null) {
            instance = new JwtUtil();
        }
        return instance;
    }

    public String getSubjectFromToken(String token) {
        String subject = null;
        try {
            subject = getClaimsFromToken(token).getSubject();
        } catch (ExpiredJwtException e) {
            subject = e.getClaims().getSubject();
        }
        return subject;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * 校验 token 是否过期，token 有效时间小于当前时间则返回 true
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getClaimsFromToken(token).getExpiration();
        return expirationDate.before(new Date());
    }

    /**
     * 校验刷新 token 是否过期，刷新 token 有效时间小于当前时间则返回 true
     *
     * @param token
     * @return
     */
    private boolean isRefreshExpired(String token) {
        Date expirationDate;
        try {
            expirationDate = new Date((Long) getClaimsFromToken(token).get(REFRESH_EXPIRATION_CLAIM_NAME));
        } catch (ExpiredJwtException e) {
            expirationDate = new Date((Long) e.getClaims().get(REFRESH_EXPIRATION_CLAIM_NAME));
        }
        return expirationDate.before(new Date());
    }

    /**
     * 判断 token 创建时间小于密码最后修改时间则返回 true
     *
     * @param createdDate
     * @param passwordLastModified
     * @return
     */
    private boolean isCreatedBeforePasswordLastModified(Date createdDate, Date passwordLastModified) {
        return passwordLastModified != null && createdDate != null && createdDate.before(passwordLastModified);
    }

    /**
     * 忽略例外 token
     *
     * @param token
     * @return
     */
    private boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    /**
     * 生成 token
     *
     * @return
     */
    public String generateToken(JwtEntity jwtEntity) {
        Map<String, Object> claims = jwtEntity.getClaims();

        if (jwtEntity.getIssuedAt() != null) {
            claims.put(REFRESH_EXPIRATION_CLAIM_NAME, calculateRefreshExpirationDate(jwtEntity.getIssuedAt()));
        }
        JwtBuilder jwt = Jwts.builder();
        if (jwtEntity.getClaims() != null) {
            jwt.setClaims(jwtEntity.getClaims());
        }
        if (jwtEntity.getId() != null) {
            jwt.setId(jwtEntity.getId());
        }
        if (jwtEntity.getSubject() != null) {
            jwt.setSubject(jwtEntity.getSubject());
        }
        if (jwtEntity.getIssuedAt() != null) {
            jwt.setIssuedAt(jwtEntity.getIssuedAt());
        }
        if (jwtEntity.getExpiration() != null) {
            jwt.setExpiration(jwtEntity.getExpiration());
        }
        if (jwtEntity.getExpiration() != null) {
            jwt.signWith(jwtProperties.getSignatureAlgorithm(), jwtProperties.getSecret());
        }
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(jwtEntity.getSubject())
//                .setIssuedAt(jwtEntity.getIssuedAt())
//                .setExpiration(jwtEntity.getExpiration())
//                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
//                .compact();
        return jwt.compact();
    }

    /**
     * 生成 token
     *
     * @return
     */
    public Map<TokenType, String> generateTokenWithRefreshToken(JwtEntity jwtEntity) {
        Map<TokenType, String> map = new HashMap<>(2);
        map.put(TokenType.ACCESS_TOKEN, generateToken(jwtEntity));
        map.put(TokenType.REFRESH_TOKEN, generateToken(jwtEntity));
        return map;
    }

    /**
     * 校验 token 是否可以刷新，token 创建时间大于密码最后修改时间且刷新 token 的有效时间大于当前时间，则返回 true
     *
     * @param token
     * @param passwordLastModified
     * @return
     */
    public boolean canTokenBeRefreshed(String token, Date passwordLastModified) {
        Date createdDate = null;
        try {
            createdDate = getClaimsFromToken(token).getIssuedAt();
        } catch (ExpiredJwtException e) {
            createdDate = e.getClaims().getIssuedAt();
        }
        return !isCreatedBeforePasswordLastModified(createdDate, passwordLastModified) && (!isRefreshExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * 根据旧 token 来刷新获取新 token
     *
     * @param token 旧 token
     * @return 新 token
     */
    public String refreshToken(String token, Date issuedAt) {
        Claims claims;
        try {
            claims = getClaimsFromToken(token);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        claims.setIssuedAt(issuedAt);
        claims.setExpiration(calculateExpirationDate(issuedAt));
        claims.put(REFRESH_EXPIRATION_CLAIM_NAME, calculateRefreshExpirationDate(issuedAt));
        return Jwts.builder().setClaims(claims).signWith(jwtProperties.getSignatureAlgorithm(), jwtProperties.getSecret()).compact();
    }

    /**
     * 校验 token 是否有效
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token, Date gmtPasswordLastModified) {
        try {
            final Date createdDate = getClaimsFromToken(token).getIssuedAt();
            // 如果 token 存在，且 token 创建日期 > 最后修改密码的日期 则代表 token 有效
            return !isTokenExpired(token) && !isCreatedBeforePasswordLastModified(createdDate, gmtPasswordLastModified);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计算得出 token 有效时间
     *
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtProperties.getExpiration().toMillis());
    }

    /**
     * 计算得出刷新 token 有效时间
     *
     * @param createdDate
     * @return
     */
    private Date calculateRefreshExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtProperties.getRefreshExpiration().toMillis());
    }

    public enum TokenType {

        /**
         * 访问 token
         */
        ACCESS_TOKEN("accessToken"),

        /**
         * 刷新 token
         */
        REFRESH_TOKEN("refreshToken");

        TokenType(String type) {
        }
    }
}
