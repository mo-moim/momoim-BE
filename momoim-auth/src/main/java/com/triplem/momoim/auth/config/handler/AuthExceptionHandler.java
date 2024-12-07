package com.triplem.momoim.auth.config.handler;

import com.triplem.momoim.exception.ExceptionCode;
import com.triplem.momoim.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ExceptionResponse response = ExceptionResponse.from(ExceptionCode.ACCESS_DENIED);
        log.error("Authorization denied: {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
