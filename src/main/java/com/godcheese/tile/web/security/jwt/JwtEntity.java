package com.godcheese.tile.web.security.jwt;

import com.godcheese.tile.util.RandomUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-05-06
 */
public class JwtEntity {

    private Map<String, Object> claims = new HashMap<>(6);

    /**
     * JWT 的唯一标识，默认随机生成的字符串
     */
    private String id = RandomUtil.randomString(10, RandomUtil.NUMBER_LETTER);
    /**
     * JWT 的签发时间，默认当前时间
     */
    private Date issuedAt = new Date();
    /**
     * JWT 生效的开始时间，默认 issueAt 时间
     */
    private Date notBefore = getIssuedAt();

    /**
     * JWT 的过期时间，默认 5 分钟过期
     */
    private Date expiration = new Date(getIssuedAt().getTime() + (5 * 60 * 1000));

    /**
     * JWT 的接收对象
     */
    private String audience = "aud";
    /**
     * JWT 的主体，即它的所有人
     */
    private String subject = "sub";

    /**
     * JWT 的签发主体
     */
    private String issuer = "jwt";

    /**
     * JWT 刷新过期时间，默认 10 分钟过期
     */
    private Date refreshExpiration = new Date(getIssuedAt().getTime() + (10 * 60 * 1000));

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
