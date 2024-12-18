package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
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
    private GatheringStatus status = GatheringStatus.OPEN;
    @Builder.Default
    private String image = "image1";
    @Builder.Default
    private String description = "description1";
    @Builder.Default
    private String address = "홍대입구역 KFC";
    @Builder.Default
    private List<String> tags = List.of("tag1", "tag2");
    @Builder.Default
    private GatheringLocation location = GatheringLocation.SEOUL;
    @Builder.Default
    private int capacity = 100;
    @Builder.Default
    private int participantCount = 10;
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
            .status(status)
            .image(image)
            .description(description)
            .address(address)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(participantCount)
            .isPeriodic(isPeriodic)
            .nextGatheringAt(nextGatheringAt)
            .createdAt(createdAt)
            .lastModifiedAt(lastModifiedAt)
            .build();
    }
}
