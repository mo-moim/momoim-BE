package com.triplem.momoim.core.domain.member;

public interface GatheringMemberRepository {
    GatheringMember save(GatheringMember gatheringMember);

    Boolean isExistsByUserIdAndGatheringId(Long userId, Long gatheringId);
}
