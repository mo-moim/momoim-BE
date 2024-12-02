package com.triplem.momoim.api.gathering.dto;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GatheringItem {
    private Long gatheringId;
    private String image;
    private String name;
    private String category;
    private String subCategory;
    private GatheringLocation location;
    private LocalDateTime nextGatheringAt;
    private List<String> tags;
    private int capacity;
    private int participantCount;

    public static GatheringItem from(Gathering gathering) {
        return GatheringItem
            .builder()
            .gatheringId(gathering.getId())
            .image(gathering.getImage())
            .name(gathering.getName())
            .category(gathering.getCategory())
            .subCategory(gathering.getSubCategory())
            .location(gathering.getLocation())
            .nextGatheringAt(gathering.getNextGatheringAt())
            .tags(gathering.getTags())
            .capacity(gathering.getCapacity())
            .participantCount(gathering.getParticipantCount())
            .build();
    }
}
