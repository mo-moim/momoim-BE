package com.triplem.momoim.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "로그아웃 응답")
public record LogoutResponse(
        @Schema(description = "로그아웃 응답 메시지", example = "SUCCESS")
        String message
) {
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    public static LogoutResponse createSuccessLogoutResponse() {
        return LogoutResponse.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
