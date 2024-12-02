package com.triplem.momoim.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationInformation {
    private int offset;
    private int limit;
}
