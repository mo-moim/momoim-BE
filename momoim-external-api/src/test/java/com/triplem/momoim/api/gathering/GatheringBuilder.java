package com.triplem.momoim.api.gathering;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
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
    private LocalDateTime nextGatheringAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    @Builder.Default
    private LocalDateTime startAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    @Builder.Default
    private LocalDateTime endAt = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    public Gathering toGathering() {
        return Gathering.builder()
            .id(id)
            .managerId(managerId)
            .category(category)
            .subCategory(subCategory)
            .name(name)
            .image(image)
            .description(description)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(participantCount)
            .nextGatheringAt(nextGatheringAt)
            .startAt(startAt)
            .endAt(endAt)
            .createdAt(createdAt)
            .build();
    }
}
