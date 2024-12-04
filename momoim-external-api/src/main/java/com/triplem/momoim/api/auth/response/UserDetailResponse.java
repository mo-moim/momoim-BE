package com.triplem.momoim.api.auth.response;

import com.triplem.momoim.core.domain.user.User;
import com.triplem.momoim.core.domain.user.UserActiveLocation;
import com.triplem.momoim.core.domain.user.UserInterestCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema
public record UserDetailResponse(
        @Schema(description = "이메일")
        String email,

        @Schema(description = "닉네임")
        String name,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "계정 유형", example="EMAIL or KAKAO")
        String accountType,

        @Schema(description = "활동할 지역", example = "['SEOUL', 'BUSAN]' or '[ALL]'")
        List<String> regions,

        @Schema(description = "관심 카테고리", example = "['CULTURE', 'FOOD', 'SPORTS', 'HOBBY', 'TRAVEL', 'STUDY', 'MEETING'] or '[ALL]'")
        List<String> interestCategories
) {
        public static UserDetailResponse from(
                User user,
                List<UserActiveLocation> userActiveLocations,
                List<UserInterestCategory> userInterestCategories
        ) {
                List<String> regions = userActiveLocations.stream()
                        .map(UserActiveLocation::getActiveLocationType)
                        .toList();

                List<String> interestCategories = userInterestCategories.stream()
                        .map(UserInterestCategory::getCategory)
                        .toList();

                return UserDetailResponse.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .profileImage(user.getProfileImage())
                        .accountType(user.getAccountType().name())
                        .regions(regions)
                        .interestCategories(interestCategories)
                        .build();
        }
}
