package com.triplem.momoim.core.domain.member;

import java.util.List;

public interface GatheringMemberRepository {
    GatheringMember save(GatheringMember gatheringMember);

    Boolean isGatheringMember(Long userId, Long gatheringId);

    void deleteByUserIdAndGatheringId(Long userId, Long gatheringId);

    List<GatheringMemberDetail> getGatheringMembers(Long gatheringId);

    GatheringMember findById(Long id);

    void deleteById(Long id);
}
