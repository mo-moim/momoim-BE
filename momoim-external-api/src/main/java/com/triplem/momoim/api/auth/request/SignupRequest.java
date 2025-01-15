package com.triplem.momoim.api.auth.request;

import com.triplem.momoim.core.domain.user.AccountType;
import com.triplem.momoim.core.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "회원가입 요청")
public record SignupRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email,

        @Schema(description = "닉네임", example = "admin")
        String name,

        @Schema(description = "비밀번호", example = "test1234!")
        String password,

        @Schema(description = "활동할 지역", example = "['SEOUL', 'BUSAN]' or '[ALL]'")
        List<String> regions,

        @Schema(description = "관심 카테고리", example = "['CULTURE', 'FOOD', 'SPORTS', 'HOBBY', 'TRAVEL', 'STUDY', 'MEETING'] or '[ALL]'")
        List<String> interestCategories
) {
        public User toUser(String password) {
                return User.builder()
                        .email(email)
                        .name(name)
                        .accountType(AccountType.EMAIL)
                        .profileImage("DEFAULT_PROFILE_IMAGE")
                        .password(password)
                        .createdAt(LocalDateTime.now())
                        .build();
        }
}
