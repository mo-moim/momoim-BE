package com.triplem.momoim.core.domain.member;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GatheringMemberDetail {
    private Long gatheringMemberId;
    private Long userId;
    private String email;
    private String name;
    private String profileImage;
    private LocalDateTime joinedAt;
}
