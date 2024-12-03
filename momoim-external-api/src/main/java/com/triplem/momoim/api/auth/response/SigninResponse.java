package com.triplem.momoim.api.auth.response;

import com.triplem.momoim.api.auth.response.common.Token;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SigninResponse(

        @Schema(description = "Access Token Credentials")
        Token accessToken,

        @Schema(description = "이메일")
        String email,

        @Schema(description = "닉네임")
        String name,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "활동할 지역", example = "['SEOUL', 'BUSAN]' or '[ALL]'")
        List<String> regions,

        @Schema(description = "관심 카테고리", example = "['CULTURE', 'FOOD', 'SPORTS', 'HOBBY', 'TRAVEL', 'STUDY', 'MEETING'] or '[ALL]'")
        List<String> interestCategories
) {
}
