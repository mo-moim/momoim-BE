package com.triplem.momoim.api.auth.response;

import com.triplem.momoim.core.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema
public record SignupResponse(
        @Schema(description = "이메일")
        String email,

        @Schema(description = "닉네임")
        String name,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "회원가입 유형")
        String accountType

) {
    public static SignupResponse from(User user) {
        return SignupResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .accountType(user.getAccountType().name())
                .build();
    }
}