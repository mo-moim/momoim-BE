package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.auth.jwt.TokenInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenCommandService {
    private static final String ACCESS_TOKEN_KEY_IN_COOKIE = "ACCESS_TOKEN";
    private static final String COOKIE_KEY_IN_HEADER = "Set-Cookie";
    private static final String COOKIE_DEFAULT_PATH_ROOT = "/";
    private static final int DEFAULT_COOKIE_AGE = 7200;

    @Value("${spring.cookie.domain}")
    private String cookieDomain;

    public void storeAccessTokenInCookie(TokenInfo tokenInfo, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_KEY_IN_COOKIE, tokenInfo.getValue())
                .path(COOKIE_DEFAULT_PATH_ROOT)
                .domain(cookieDomain)
                .secure(true)
                .httpOnly(true)
                .maxAge(DEFAULT_COOKIE_AGE)
                .build();
        response.addHeader(COOKIE_KEY_IN_HEADER, cookie.toString());
    }
}
