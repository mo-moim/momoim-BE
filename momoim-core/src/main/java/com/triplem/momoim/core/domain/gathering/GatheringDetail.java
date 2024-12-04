package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GatheringDetail {
    private Long id;
    private Long managerId;
    private String managerName;
    private String managerProfileImage;
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
    private Boolean isManager;
}
