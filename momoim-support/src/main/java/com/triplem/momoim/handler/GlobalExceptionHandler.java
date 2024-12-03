package com.triplem.momoim.handler;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import com.triplem.momoim.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {
        ExceptionResponse response = ExceptionResponse.from(ex.getExceptionCode());
        log.error("Business exception has occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleExceptionHandler(Exception ex) {
        ExceptionResponse response = ExceptionResponse.from(ExceptionCode.INTERNAL_SERVER_ERROR);
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
