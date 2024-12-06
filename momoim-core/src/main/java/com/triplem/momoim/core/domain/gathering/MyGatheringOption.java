package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.common.PaginationInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyGatheringOption {
    private Boolean isOnlyIMade;
    private PaginationInformation paginationInformation;
}
