package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Gathering {
    private Long id;
    private Long managerId;
    private String category;
    private String subCategory;
    private RecruitStatus recruitStatus;
    private String name;
    private String image;
    private int capacity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
}
