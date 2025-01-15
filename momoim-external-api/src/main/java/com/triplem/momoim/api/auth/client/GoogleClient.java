package com.triplem.momoim.api.auth.client;

import com.triplem.momoim.api.auth.client.dto.google.GoogleInfo;
import com.triplem.momoim.api.auth.client.dto.google.GoogleToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "GoogleClient")
public interface GoogleClient {

    @GetMapping
    GoogleInfo getProfile(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @PostMapping
    GoogleToken getToken(URI baseUrl, @RequestParam("client_id") String clientId,
                         @RequestParam("client_secret") String clientSecretKey,
                         @RequestParam("redirect_uri") String redirectUrl,
                         @RequestParam("code") String code,
                         @RequestParam("grant_type") String grantType);
}
