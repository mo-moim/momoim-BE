package com.triplem.momoim.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultApiResponse {
    private Boolean success;

    public static DefaultApiResponse success() {
        return new DefaultApiResponse(true);
    }
}
