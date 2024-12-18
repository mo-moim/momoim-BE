package com.triplem.momoim.core.domain.gathering.dto;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.member.dto.GatheringMemberDetail;
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
    private GatheringStatus status;
    private String category;
    private String subCategory;
    private GatheringLocation location;
    private LocalDateTime nextGatheringAt;
    private List<String> tags;
    private int capacity;
    private int participantCount;
    private Boolean isWishlist;
    private Boolean isPeriodic;
    private List<GatheringMemberDetail> members;
}
