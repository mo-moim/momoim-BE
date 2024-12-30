package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.auth.jwt.TokenInfo;
import com.triplem.momoim.core.domain.user.auth.RefreshToken;
import com.triplem.momoim.core.domain.user.auth.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenCommandService {
    private static final String ACCESS_TOKEN_KEY_IN_COOKIE = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN_KEY_IN_COOKIE = "REFRESH_TOKEN";
    private static final String COOKIE_KEY_IN_HEADER = "Set-Cookie";
    private static final String COOKIE_DEFAULT_PATH_ROOT = "/";
    private static final int DEFAULT_ACCESS_TOKEN_COOKIE_AGE = 7200;
    private static final int DEFAULT_REFRESH_TOKEN_COOKIE_AGE = 1209600;
    private static final List<String> DEFAULT_COOKIE_DOMAINS = List.of("localhost", "momoim.co.kr");

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.cookie.domain}")
    private String cookieDomain;

    public void storeAccessTokenInCookie(TokenInfo tokenInfo, HttpServletResponse response) {
        for (String domain : DEFAULT_COOKIE_DOMAINS) {
            if (domain.equals("localhost")) {
                ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_KEY_IN_COOKIE, tokenInfo.getValue())
                        .path(COOKIE_DEFAULT_PATH_ROOT)
                        .httpOnly(true)
                        .maxAge(DEFAULT_ACCESS_TOKEN_COOKIE_AGE)
                        .build();
                response.addHeader(COOKIE_KEY_IN_HEADER, cookie.toString());
            } else {
                ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_KEY_IN_COOKIE, tokenInfo.getValue())
                        .path(COOKIE_DEFAULT_PATH_ROOT)
                        .domain(domain)
                        .httpOnly(true)
                        .maxAge(DEFAULT_ACCESS_TOKEN_COOKIE_AGE)
                        .secure(true)
                        .build();
                response.addHeader(COOKIE_KEY_IN_HEADER, cookie.toString());
            }

        }
    }

    public void storeRefreshTokenInCookie(Long userId, TokenInfo tokenInfo, HttpServletResponse response) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(tokenInfo.getValue())
                .expiredAt(tokenInfo.getExpiredTime())
                .build();

        refreshTokenRepository.save(refreshToken);

        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_KEY_IN_COOKIE, tokenInfo.getValue())
                .path(COOKIE_DEFAULT_PATH_ROOT)
                .domain(cookieDomain)
                .httpOnly(true)
                .maxAge(DEFAULT_REFRESH_TOKEN_COOKIE_AGE)
                .build();
        response.addHeader(COOKIE_KEY_IN_HEADER, cookie.toString());
    }

    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    public void removeRefreshTokenInCookie(HttpServletResponse response, Cookie refreshToken) {
        refreshToken.setMaxAge(0);
        refreshToken.setDomain(cookieDomain);
        refreshToken.setPath("/");
        response.addCookie(refreshToken);
    }
}
