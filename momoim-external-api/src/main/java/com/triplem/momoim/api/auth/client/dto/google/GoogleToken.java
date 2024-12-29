package com.triplem.momoim.api.auth.client.dto.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleToken {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private int expiresIn;

}
