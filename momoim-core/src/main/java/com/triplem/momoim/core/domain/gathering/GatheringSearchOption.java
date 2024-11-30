package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private String category;
    private String subCategory;
    private GatheringLocation location;
    private LocalDate gatheringDate;
    private Long managerId;
    private GatheringSearchSortType sortType;
    private GatheringSearchSortOrder sortOrder;
    private int offset;
    private int limit;
}
