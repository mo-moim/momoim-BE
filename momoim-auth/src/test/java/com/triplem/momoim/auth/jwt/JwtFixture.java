package com.triplem.momoim.auth.jwt;


public class JwtFixture {

    private static final String ACCESS_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA==";
    private static final String REFRESH_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA==";

    private static final String INVALID_ACCESS_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA=!";
    private static final String INVALID_REFRESH_TOKEN_SECRET_KEY = "ArACpIW3Yf2+VgWHZdButiZrYy6+VZnsL2/AGj8fVyDxQwL8Y7v4QEzs4BZrk49mWnwHS2WPuF8DP83+Sjk3OA=!";

    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1800000L;  // 30 minutes
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 3600000L;  // 1 hour

    public static JwtProvider JWT_PROVIDER() {
        return new JwtProvider(createJwtProperties(ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME, REFRESH_TOKEN_SECRET_KEY, REFRESH_TOKEN_EXPIRE_TIME));
    }

    public static JwtResolver JWT_RESOLVER() {
        return new JwtResolver(createJwtProperties(ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME, REFRESH_TOKEN_SECRET_KEY, REFRESH_TOKEN_EXPIRE_TIME));
    }

    public static JwtProvider JWT_PROVIDER_WITH_INVALID_SECRET_KEY() {
        return new JwtProvider(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, ACCESS_TOKEN_EXPIRE_TIME, REFRESH_TOKEN_SECRET_KEY, REFRESH_TOKEN_EXPIRE_TIME));
    }

    public static JwtProvider JWT_PROVIDER_WITH_EXPIRED_TOKEN() {
        return new JwtProvider(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, -1000000L, REFRESH_TOKEN_SECRET_KEY, REFRESH_TOKEN_EXPIRE_TIME));
    }

    public static JwtResolver JWT_RESOLVER_WITH_EXPIRED_TOKEN() {
        return new JwtResolver(createJwtProperties(INVALID_ACCESS_TOKEN_SECRET_KEY, -1000000L, REFRESH_TOKEN_SECRET_KEY, REFRESH_TOKEN_EXPIRE_TIME));
    }

    private static JwtProperties createJwtProperties(
            String accessTokenSecretKey,
            Long accessTokenExpireTime,
            String refreshTokenSecretKey,
            Long refreshTokenExpireTime
    ) {
        return new JwtProperties(
                accessTokenSecretKey,
                accessTokenExpireTime,
                refreshTokenSecretKey,
                refreshTokenExpireTime
        );
    }
}
