package com.triplem.momoim.api.auth.response;

import com.triplem.momoim.api.auth.response.common.Token;
import com.triplem.momoim.auth.jwt.TokenInfo;
import com.triplem.momoim.core.domain.user.User;
import com.triplem.momoim.core.domain.user.UserActiveLocation;
import com.triplem.momoim.core.domain.user.UserInterestCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema
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
        public static SigninResponse from(
                User user,
                TokenInfo tokenInfo,
                List<UserActiveLocation> userActiveLocations,
                List<UserInterestCategory> userInterestCategories
        ) {
                Token token = new Token(tokenInfo.getValue(), tokenInfo.getExpiredTime().getTime());

                List<String> regions = userActiveLocations.stream()
                        .map(UserActiveLocation::getActiveLocationType)
                        .toList();

                List<String> interestCategories = userInterestCategories.stream()
                        .map(UserInterestCategory::getCategory)
                        .toList();

                return SigninResponse.builder()
                        .accessToken(token)
                        .email(user.getEmail())
                        .name(user.getName())
                        .profileImage(user.getProfileImage())
                        .regions(regions)
                        .interestCategories(interestCategories)
                        .build();
        }
}
