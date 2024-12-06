package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.enums.GatheringType;
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GatheringPreview {
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
    private List<GatheringMemberDetail> members;

    public static GatheringPreview from(Gathering gathering) {
        return GatheringPreview
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
