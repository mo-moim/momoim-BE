package com.triplem.momoim.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    // =============================================================
    // ==                        COMMON                           ==
    // =============================================================
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "오류가 발생했습니다."),

    // =============================================================
    // ==                         AUTH                            ==
    // =============================================================
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "EXPIRED_JWT", "만료된 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
