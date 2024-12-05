package com.triplem.momoim.api.auth.request;

import com.triplem.momoim.core.domain.user.AccountType;
import com.triplem.momoim.core.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record UserProfileUpdateRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email,

        @Schema(description = "닉네임", example = "admin")
        String name,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "활동할 지역", example = "['SEOUL', 'BUSAN]' or '[ALL]'")
        List<String> regions,

        @Schema(description = "관심 카테고리", example = "['CULTURE', 'FOOD', 'SPORTS', 'HOBBY', 'TRAVEL', 'STUDY', 'MEETING'] or '[ALL]'")
        List<String> interestCategories
) {
        public User toUpdatedUser(User user) {
                return User.builder()
                        .id(user.getId())
                        .email(email)
                        .name(name)
                        .accountType(user.getAccountType())
                        .profileImage(profileImage)
                        .password(user.getPassword())
                        .socialUid(user.getSocialUid())
                        .createdAt(user.getCreatedAt())
                        .build();
        }
}
