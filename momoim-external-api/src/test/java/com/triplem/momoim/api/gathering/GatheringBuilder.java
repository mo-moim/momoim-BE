package com.triplem.momoim.api.gathering;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.GatheringType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public class GatheringBuilder {
    private Long id;
    @Builder.Default
    private Long managerId = 1L;
    @Builder.Default
    private String category = "FOOD";
    @Builder.Default
    private String subCategory = "COOK";
    @Builder.Default
    private String name = "gathering1";
    @Builder.Default
    private GatheringType gatheringType = GatheringType.OFFLINE;
    @Builder.Default
    private GatheringStatus status = GatheringStatus.RECRUITING;
    @Builder.Default
    private String image = "image1";
    @Builder.Default
    private String description = "description1";
    @Builder.Default
    private List<String> tags = List.of("tag1", "tag2");
    @Builder.Default
    private GatheringLocation location = GatheringLocation.SEOUL;
    @Builder.Default
    private int capacity = 100;
    @Builder.Default
    private int participantCount = 10;
    @Builder.Default
    private int viewCount = 0;
    @Builder.Default
    private Boolean isPeriodic = false;
    @Builder.Default
    private LocalDateTime nextGatheringAt = LocalDateTime.now().plusDays(5);
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now().minusYears(1);
    @Builder.Default
    private LocalDateTime lastModifiedAt = LocalDateTime.now().minusHours(3);

    public Gathering toGathering() {
        return Gathering.builder()
            .id(id)
            .managerId(managerId)
            .category(category)
            .subCategory(subCategory)
            .name(name)
            .gatheringType(gatheringType)
            .status(status)
            .image(image)
            .description(description)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(participantCount)
            .viewCount(viewCount)
            .isPeriodic(isPeriodic)
            .nextGatheringAt(nextGatheringAt)
            .createdAt(createdAt)
            .lastModifiedAt(lastModifiedAt)
            .build();
    }
}
