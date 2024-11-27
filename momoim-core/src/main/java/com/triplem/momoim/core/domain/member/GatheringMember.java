package com.triplem.momoim.core.domain.member;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GatheringMember {
    private Long id;
    private Long userId;
    private Long gatheringId;
    private LocalDateTime joinedAt;

    public static GatheringMember create(Long userId, Long gatheringId) {
        return GatheringMember.builder()
            .userId(userId)
            .gatheringId(gatheringId)
            .joinedAt(LocalDateTime.now())
            .build();
    }
}
