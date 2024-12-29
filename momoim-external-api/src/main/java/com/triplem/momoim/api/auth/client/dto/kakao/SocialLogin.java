package com.triplem.momoim.api.auth.client.dto.kakao;

public interface SocialLogin {
    String getNickName();
    Long getId();
    String getGender();
    String getAgeRange();
    String getEmail();
    String getProfileImageUrl();
}