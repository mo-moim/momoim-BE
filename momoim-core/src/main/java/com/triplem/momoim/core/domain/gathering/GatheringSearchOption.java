package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;
import java.util.List;

public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private String category;
    private GatheringLocation location;
    private LocalDateTime gatheringAt;
    private Long managerId;
    private GatheringSearchSortType sortType;
    private GatheringSearchSortOrder sortOrder;
    private int offset;
    private int limit;
}
