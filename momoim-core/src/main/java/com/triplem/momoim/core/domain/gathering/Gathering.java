package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Gathering {
    private Long id;
    private Long managerId;
    private GatheringCategory category;
    private GatheringSubCategory subCategory;
    private RecruitStatus recruitStatus;
    private String name;
    private String image;
    private int capacity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
}
