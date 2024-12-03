package com.triplem.momoim.api.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 로그인 요청")
public record SigninRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email,

        @Schema(description = "비밀번호", example = "test1234!")
        String password
) {}
