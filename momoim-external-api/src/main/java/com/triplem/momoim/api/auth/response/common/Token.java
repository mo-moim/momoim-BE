package com.triplem.momoim.api.auth.response.common;

import io.swagger.v3.oas.annotations.media.Schema;

public record Token(
        @Schema(description = "토큰", example = "token")
        String token,

        @Schema(description = "토큰 만료 시간", example = "6450340")
        Long expiredAt
) {}
