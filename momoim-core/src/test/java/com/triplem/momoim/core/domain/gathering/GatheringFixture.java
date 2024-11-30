package com.triplem.momoim.core.domain.gathering;

import java.time.LocalDateTime;
import java.util.List;

public class GatheringFixture {
    public static Gathering createGathering(Long managerId, RecruitStatus recruitStatus, int capacity, int participantCount) {
        return Gathering.builder()
            .managerId(managerId)
            .category("FOOD")
            .subCategory("COOK")
            .recruitStatus(recruitStatus)
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .description("요리를 배우며 즐기는 모임입니다.")
            .tags(List.of("초보 환영", "재료비 지원"))
            .location(GatheringLocation.INCHEON)
            .capacity(capacity)
            .participantCount(participantCount)
            .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 0, 0))
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(LocalDateTime.now())
            .build();
    }
}
