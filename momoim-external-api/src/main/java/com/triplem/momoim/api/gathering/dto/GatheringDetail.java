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
public class GatheringDetail {
    private Long id;
    private Long managerId;
    private String category;
    private String subCategory;
    private String name;
    private GatheringType gatheringType;
    private GatheringStatus status;
    private String image;
    private String description;
    private String address;
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private int participantCount;
    private Boolean isPeriodic;
    private LocalDateTime nextGatheringAt;
    private Boolean isJoined;

    public static GatheringDetail of(Gathering gathering, Boolean isJoined) {
        return GatheringDetail.builder()
            .id(gathering.getId())
            .managerId(gathering.getManagerId())
            .category(gathering.getCategory())
            .subCategory(gathering.getSubCategory())
            .name(gathering.getName())
            .gatheringType(gathering.getGatheringType())
            .status(gathering.getStatus())
            .image(gathering.getImage())
            .description(gathering.getDescription())
            .address(gathering.getAddress())
            .tags(gathering.getTags())
            .location(gathering.getLocation())
            .capacity(gathering.getCapacity())
            .participantCount(gathering.getParticipantCount())
            .isPeriodic(gathering.getIsPeriodic())
            .nextGatheringAt(gathering.getNextGatheringAt())
            .isJoined(isJoined)
            .build();
    }
}
