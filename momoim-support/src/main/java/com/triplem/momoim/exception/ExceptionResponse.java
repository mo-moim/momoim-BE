package com.triplem.momoim.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ExceptionResponse
 * This class represents the structure of the exception response sent to the client when an error occurs.
 * It includes the HTTP status code, error code, and a descriptive message.
 *
 * TODO:
 * - Add support for field-specific errors, useful for validation failures or detailed error handling.
 * - Add a field to store detailed field error information.
 * - Implement a static method to create an ExceptionResponse containing field-specific errors.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    @JsonIgnore
    private HttpStatus status;
    private String code;
    private String message;

    @Builder
    private ExceptionResponse(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * Static factory method to create an ExceptionResponse object from an
     * {@link ExceptionCode}.
     *
     * @param exceptionCode the ExceptionCode containing error details
     * @return a new ExceptionResponse object
     */
    public static ExceptionResponse from(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getStatus())
                .code(exceptionCode.getCode())
                .message(exceptionCode.getMessage())
                .build();
    }
}
