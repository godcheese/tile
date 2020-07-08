package com.godcheese.tile.web.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2020-05-06
 */
public class JwtProperties {

    private String header = "Authorization";

    private String secret = "tile";

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;;

    private Duration expiration = Duration.ofMinutes(1);

    private String bearerType = "Bearer";

    private String generateTokenPath = "/token";

    private String refreshTokenPath = "/refresh_token";

    private Duration refreshExpiration = Duration.ofMinutes(5);

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }

    public String getBearerType() {
        return bearerType;
    }

    public void setBearerType(String bearerType) {
        this.bearerType = bearerType;
    }

    public String getGenerateTokenPath() {
        return generateTokenPath;
    }

    public void setGenerateTokenPath(String generateTokenPath) {
        this.generateTokenPath = generateTokenPath;
    }

    public String getRefreshTokenPath() {
        return refreshTokenPath;
    }

    public void setRefreshTokenPath(String refreshTokenPath) {
        this.refreshTokenPath = refreshTokenPath;
    }

    public Duration getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(Duration refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }
}
