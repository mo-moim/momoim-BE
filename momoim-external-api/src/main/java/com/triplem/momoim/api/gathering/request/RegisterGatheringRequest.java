package com.triplem.momoim.api.gathering.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class RegisterGatheringRequest {
    private GatheringCategory category;
    private GatheringSubCategory subCategory;
    private String name;
    private String image;
    private String description;
    private String address;
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private Boolean isPeriodic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime nextGatheringAt;

    public Gathering toGathering(Long managerId) {
        return Gathering.builder()
            .managerId(managerId)
            .category(category.name())
            .subCategory(subCategory.name())
            .name(name)
            .status(GatheringStatus.OPEN)
            .image(image)
            .description(description)
            .address(address)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(0)
            .wishlistCount(0)
            .isPeriodic(isPeriodic)
            .nextGatheringAt(nextGatheringAt)
            .createdAt(LocalDateTime.now())
            .lastModifiedAt(LocalDateTime.now())
            .build();
    }
}
