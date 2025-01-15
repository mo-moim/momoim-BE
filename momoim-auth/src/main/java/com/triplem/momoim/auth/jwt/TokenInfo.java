package com.triplem.momoim.auth.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {
    private String value;
    private Date expiredTime;

    @Builder
    public TokenInfo(String value, Date expiredTime) {
        this.value = value;
        this.expiredTime = expiredTime;
    }

    public static TokenInfo of(String value, Date expiredTime) {
        return TokenInfo.builder()
                .value(value)
                .expiredTime(expiredTime)
                .build();
    }
}
