package com.triplem.momoim.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record CheckEmailNicknameResponse(
        String status
) {
}
