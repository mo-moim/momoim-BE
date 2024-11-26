package com.triplem.momoim.core.domain.member;

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
}
