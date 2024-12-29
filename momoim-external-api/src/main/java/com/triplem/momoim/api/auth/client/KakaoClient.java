package com.triplem.momoim.api.auth.client;

import com.triplem.momoim.api.auth.client.dto.kakao.KakaoInfo;
import com.triplem.momoim.api.auth.client.dto.kakao.KakaoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "KakaoClient")
public interface KakaoClient {

    @PostMapping
    KakaoInfo getProfile(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @PostMapping
    KakaoToken getToken(URI baseUrl, @RequestParam("client_id") String restAPIKey,
                        @RequestParam("redirect_uri") String redirectUrl,
                        @RequestParam("code") String code,
                        @RequestParam("grant_type") String grantType);
}