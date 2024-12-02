package com.triplem.momoim.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionCode exceptionCode;
    private final String message;

    public BusinessException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
        this.message = exceptionCode.getMessage();
    }

    /**
     * Buessiness exception을 생성할 시 전달되는 message에 값을 주입할 때 사용
     * @param exceptionCode : exception code object
     * @param args : for generating error message
     * @apiNote 사용자의 이름과 같은 값을 error message에 전달해야 할 때
     */
    public BusinessException(ExceptionCode exceptionCode, Object... args) {
        this.exceptionCode = exceptionCode;
        this.message = String.format(exceptionCode.getMessage(), args);
    }
}
