package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.client.GoogleClient;
import com.triplem.momoim.api.auth.client.dto.google.GoogleInfo;
import com.triplem.momoim.api.auth.client.dto.google.GoogleToken;
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
public class GoogleLoginCommandService {
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String AUTH_URI = "https://oauth2.googleapis.com/token";
    private static final String USER_PROFILE_URL = "https://www.googleapis.com/userinfo/v2/me";
    private final GoogleClient googleClient;
    private final AuthCommandService authCommandService;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret-key}")
    private String clientSecretKey;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    public SigninResponse googleLogin(String code, HttpServletResponse response) throws URISyntaxException {
        GoogleToken googleToken = getToken(code);

        GoogleInfo info = googleClient.getProfile(
                new URI(USER_PROFILE_URL),
                googleToken.getTokenType() + " " + googleToken.getAccessToken()
        );
        return authCommandService.socialLogin(
                info.getEmail(),
                info.getName(),
                info.getPicture(),
                AccountType.GOOGLE,
                response
        );
    }

    private GoogleToken getToken(String code) throws URISyntaxException {
        return googleClient.getToken(
                new URI(AUTH_URI),
                clientId,
                clientSecretKey,
                redirectUri,
                code,
                AUTHORIZATION_CODE
        );
    }
}
