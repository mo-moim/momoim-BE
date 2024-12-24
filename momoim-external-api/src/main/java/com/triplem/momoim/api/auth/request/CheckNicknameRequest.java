package com.triplem.momoim.api.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "닉네임 중복 체크 요청")
public record CheckNicknameRequest(
        @Schema(description = "닉네임", example = "admin")
        String name
) {
}
