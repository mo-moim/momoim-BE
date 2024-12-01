package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;

public class GatheringFixture {
    public static Gathering createGathering(Long managerId, RecruitStatus recruitStatus, int capacity, int participantCount) {
        return Gathering.builder()
            .managerId(managerId)
            .category("FOOD")
            .subCategory("COOK")
            .recruitStatus(recruitStatus)
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .capacity(capacity)
            .participantCount(participantCount)
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(LocalDateTime.now())
            .build();
    }
}
