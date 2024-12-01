package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.common.PaginationInformation;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private String category;
    private String subCategory;
    private GatheringLocation location;
    private LocalDate gatheringDate;
    private Long managerId;
    private GatheringSearchSortType sortType;
    private GatheringSearchSortOrder sortOrder;
    private PaginationInformation paginationInformation;
}
