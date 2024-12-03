package com.triplem.momoim.core.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringMemberJpaRepository extends JpaRepository<GatheringMemberEntity, Long> {
    Boolean existsByUserIdAndGatheringId(Long userId, Long gatheringId);

    void deleteByUserIdAndGatheringId(Long userId, Long gatheringId);
}
