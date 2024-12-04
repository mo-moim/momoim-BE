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
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "해당 기능에 대한 또는 리소스에 대한 접근 권한이 없습니다."),
    UNKNOWN_JWT_VALIDATE_ERROR(HttpStatus.UNAUTHORIZED, "UNKNOWN_JWT_VALIDATE_ERROR", "알 수 없는 토큰 검증 오류입니다."),
    UNSUPPORTED_BEARER_FORMAT(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_BEARER_FORMAT", "지원하지 않는 Bearer token 형식입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "EXPIRED_JWT", "만료된 토큰입니다."),
    INVALID_PRINCIPAL(HttpStatus.BAD_REQUEST, "INVALID_PRINCIPAL", "로그인 객체의 정보가 유효하지 않거나, 존재하지 않습니다."),

    // =============================================================
    // ==                         Member                          ==
    // =============================================================
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "NOT_FOUND_MEMBER", "해당하는 회원이 존재하지 않습니다."),
    INVALID_MEMBER_HAS_DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_HAS_DUPLICATED_EMAIL", "같은 이메일을 통해 이미 가입되어 있는 회원입니다."),
    INVALID_MEMBER_PROFILE_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_PROFILE_REQUEST", "로그인이 되어있지 않고 프로필을 조회할 수 없습니다."),

    // =============================================================
    // ==                         Gathering                       ==
    // =============================================================
    FULL_PARTICIPANT_GATHERING(HttpStatus.BAD_REQUEST, "FULL_PARTICIPANT_GATHERING", "인원이 다 찬 모임입니다."),
    NOT_RECRUITING_GATHERING(HttpStatus.BAD_REQUEST, "NOT_RECRUITING_GATHERING", "모집 중인 모임이 아닙니다."),
    NOT_FOUND_GATHERING(HttpStatus.NOT_FOUND, "NOT_FOUND_GATHERING", "존재하지 않는 모임입니다."),
    FORBIDDEN_GATHERING(HttpStatus.FORBIDDEN, "FORBIDDEN_GATHERING", "권한이 없습니다."),
    NOT_GATHERING_MEMBER(HttpStatus.FORBIDDEN, "NOT_GATHERING_MEMBER", "모임 멤버가 아닙니다."),
    UNAVAILABLE_MANAGER_LEAVE(HttpStatus.BAD_REQUEST, "UNAVAILABLE_MANAGER_LEAVE", "모임 매니저는 모임을 나갈 수 없습니다."),
    ALREADY_JOINED_GATHERING(HttpStatus.CONFLICT, "ALREADY_JOINED_GATHERING", "이미 참여중인 모임입니다."),

    // =============================================================
    // ==                         Review                          ==
    // =============================================================
    FORBIDDEN_REVIEW(HttpStatus.FORBIDDEN, "FORBIDDEN_REVIEW", "권한이 없습니다."),
    ALREADY_REVIEWED(HttpStatus.CONFLICT, "ALREADY_REVIEWED", "이미 리뷰를 작성한 모임입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
