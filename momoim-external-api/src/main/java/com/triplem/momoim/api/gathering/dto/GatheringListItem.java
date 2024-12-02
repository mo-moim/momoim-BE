package com.triplem.momoim.api.gathering.dto;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.GatheringType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GatheringListItem {
    private Long gatheringId;
    private String image;
    private String name;
    private GatheringType gatheringType;
    private GatheringStatus status;
    private String category;
    private String subCategory;
    private GatheringLocation location;
    private LocalDateTime nextGatheringAt;
    private List<String> tags;
    private int capacity;
    private int participantCount;
    private Boolean isPeriodic;

    public static GatheringListItem from(Gathering gathering) {
        return GatheringListItem
            .builder()
            .gatheringId(gathering.getId())
            .image(gathering.getImage())
            .name(gathering.getName())
            .gatheringType(gathering.getGatheringType())
            .status(gathering.getStatus())
            .category(gathering.getCategory())
            .subCategory(gathering.getSubCategory())
            .location(gathering.getLocation())
            .nextGatheringAt(gathering.getNextGatheringAt())
            .tags(gathering.getTags())
            .capacity(gathering.getCapacity())
            .participantCount(gathering.getParticipantCount())
            .isPeriodic(gathering.getIsPeriodic())
            .build();
    }
}
