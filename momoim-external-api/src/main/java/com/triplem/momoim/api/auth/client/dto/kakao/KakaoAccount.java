package com.triplem.momoim.api.auth.client.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAccount {
    private Profile profile;
    private String email;
    private String gender;
    private String ageRange;
    private String profileImageURL;
}
