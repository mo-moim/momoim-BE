package com.triplem.momoim.auth.jwt;


public class JwtFixture {

    private static final String ACCESS_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA==";
    private static final String INVALID_ACCESS_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA=!";
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1800000L;  // 30 minutes

    public static JwtProvider JWT_PROVIDER() {
        return new JwtProvider(createJwtProperties(ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME));
    }

    public static JwtResolver JWT_RESOLVER() {
        return new JwtResolver(createJwtProperties(ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME));
    }

    public static JwtProvider JWT_PROVIDER_WITH_INVALID_SECRET_KEY() {
        return new JwtProvider(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME));
    }

    public static JwtProvider JWT_PROVIDER_WITH_EXPIRED_TOKEN() {
        return new JwtProvider(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, -1000000L));
    }

    public static JwtResolver JWT_RESOLVER_WITH_EXPIRED_TOKEN() {
        return new JwtResolver(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, -1000000L));
    }

    private static JwtProperties createJwtProperties(String secretKey, Long expireTime) {
        return new JwtProperties(secretKey, expireTime);
    }
}
