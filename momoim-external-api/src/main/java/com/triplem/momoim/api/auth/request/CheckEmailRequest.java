package com.triplem.momoim.api.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 중복 체크 요청")
public record CheckEmailRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email
) {
}
