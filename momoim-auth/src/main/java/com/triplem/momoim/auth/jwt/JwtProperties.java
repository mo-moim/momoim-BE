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

    public JwtProperties(
            @Value("${jwt.access-token-secret-key}") String accessTokenSecretKey,
            @Value("${jwt.access-token-expire-time}") long accessTokenExpireTime
    ) {
        this.accessTokenSecretKey = accessTokenSecretKey;
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    protected SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
    }

    protected long getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }
}
