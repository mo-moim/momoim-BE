package com.triplem.momoim.core.domain.schedule.dto;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GatheringSchedule {
    private Long gatheringId;
    private String gatheringName;
    private String gatheringImage;
    private GatheringType gatheringType;
    private GatheringLocation gatheringLocation;
    private String category;
    private String subCategory;
    private LocalDateTime nextGatheringAt;
    private Boolean isWishlist;
}
