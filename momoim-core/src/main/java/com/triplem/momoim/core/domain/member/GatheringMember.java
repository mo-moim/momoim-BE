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
}
