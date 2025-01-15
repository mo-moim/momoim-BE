package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.client.KakaoClient;
import com.triplem.momoim.api.auth.client.dto.kakao.KakaoInfo;
import com.triplem.momoim.api.auth.client.dto.kakao.KakaoToken;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.core.domain.user.AccountType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class KakaoLoginCommandService {

    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String AUTH_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private final KakaoClient kakaoClient;
    private final AuthCommandService authCommandService;

    @Value("${kakao.rest-api-key}")
    private String restAPIKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public SigninResponse kakaoLogin(String code, HttpServletResponse response) throws URISyntaxException{
        KakaoToken kakaoToken = getToken(code);

        KakaoInfo info = kakaoClient.getProfile(
                new URI(USER_PROFILE_URL),
                kakaoToken.getTokenType() + " " + kakaoToken.getAccessToken()
        );
        log.info("kakao {} {} {}", info.getEmail(), info.getNickName(), info.getProfileImageUrl());
        return authCommandService.socialLogin(
                info.getEmail(),
                info.getNickName(),
                info.getProfileImageUrl(),
                AccountType.KAKAO,
                response
        );
    }

    private KakaoToken getToken(String code) throws URISyntaxException {
        return kakaoClient.getToken(new URI(AUTH_URI), restAPIKey, redirectUri, code, AUTHORIZATION_CODE);
    }
}
