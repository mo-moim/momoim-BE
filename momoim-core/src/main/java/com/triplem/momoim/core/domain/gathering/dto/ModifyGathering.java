package com.triplem.momoim.core.domain.gathering.dto;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyGathering {
    private Long gatheringId;
    private GatheringSubCategory subCategory;
    private String name;
    private GatheringStatus status;
    private String image;
    private String description;
    private String address;
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private Boolean isPeriodic;
    private LocalDateTime nextGatheringAt;
}
