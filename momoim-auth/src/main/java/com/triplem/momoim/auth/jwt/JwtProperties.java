package com.triplem.momoim.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtProperties {
    protected static final String TOKEN_TYPE = "token_type";

    private final String accessTokenSecretKey;
    private final long accessTokenExpireTime;
    private final String refreshTokenSecretKey;
    private final long refreshTokenExpireTime;

    public JwtProperties(
            @Value("${jwt.access-token-secret-key}") String accessTokenSecretKey,
            @Value("${jwt.access-token-expire-time}") long accessTokenExpireTime,
            @Value("${jwt.refresh-token-secret-key}") String refreshTokenSecretKey,
            @Value("${jwt.refresh-token-expire-time}") long refreshTokenExpireTime
    ) {
        this.accessTokenSecretKey = accessTokenSecretKey;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    protected SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
    }
    protected SecretKey getRefreshTokenSecretKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
    }

    protected long getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }
    protected long getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }
}
