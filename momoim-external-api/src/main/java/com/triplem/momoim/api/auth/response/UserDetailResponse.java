package com.triplem.momoim.api.auth.response;

import com.triplem.momoim.core.domain.user.User;
import com.triplem.momoim.core.domain.user.UserActiveLocation;
import com.triplem.momoim.core.domain.user.UserInterestCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        @Schema(description = "관심 카테고리", example = "{'CULTURE': ['MOVIE', 'CONCERT'], 'FOOD': ['COOKING']]")
        Map<String, List<String>> interestCategories
) {
        public static UserDetailResponse from(
                User user,
                List<UserActiveLocation> userActiveLocations,
                List<UserInterestCategory> userInterestCategories
        ) {
                List<String> regions = userActiveLocations.stream()
                        .map(UserActiveLocation::getActiveLocationType)
                        .toList();

                Set<String> allCategories = userInterestCategories.stream()
                        .map(UserInterestCategory::getCategory)
                        .collect(Collectors.toSet());

                Map<String, List<String>> interestCategories = userInterestCategories.stream()
                        .filter(interest -> interest.getSubCategory() != null && !interest.getSubCategory().isEmpty())
                        .collect(Collectors.groupingBy(
                                UserInterestCategory::getCategory,
                                Collectors.mapping(UserInterestCategory::getSubCategory, Collectors.toList())
                        ));

                allCategories.forEach(category ->
                        interestCategories.putIfAbsent(category, Collections.emptyList())
                );

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
