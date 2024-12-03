package com.triplem.momoim.api.gathering.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.ModifyGathering;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ModifyGatheringRequest {
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime nextGatheringAt;

    public ModifyGathering toContent(Long gatheringId) {
        return ModifyGathering.builder()
            .gatheringId(gatheringId)
            .subCategory(subCategory)
            .name(name)
            .status(status)
            .image(image)
            .description(description)
            .address(address)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .isPeriodic(isPeriodic)
            .nextGatheringAt(nextGatheringAt)
            .build();
    }
}
