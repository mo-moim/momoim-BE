package com.triplem.momoim.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }
}
