package com.triplem.momoim.api.gathering.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
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
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime nextGatheringAt;

    public Gathering toGathering(Long managerId) {
        return Gathering.builder()
            .managerId(managerId)
            .category(category.name())
            .subCategory(subCategory.name())
            .name(name)
            .image(image)
            .description(description)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(0)
            .viewCount(0)
            .isCanceled(false)
            .nextGatheringAt(nextGatheringAt)
            .startAt(startAt)
            .endAt(endAt)
            .createdAt(LocalDateTime.now())
            .lastModifiedAt(LocalDateTime.now())
            .build();
    }
}
