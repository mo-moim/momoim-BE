package com.triplem.momoim.core.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringMemberRepositoryImpl implements GatheringMemberRepository {
    private final GatheringMemberJpaRepository gatheringMemberJpaRepository;

    @Override
    public GatheringMember save(GatheringMember gatheringMember) {
        return gatheringMemberJpaRepository.save(GatheringMemberEntity.from(gatheringMember)).toModel();
    }

    @Override
    public Boolean isGatheringMember(Long userId, Long gatheringId) {
        return gatheringMemberJpaRepository.existsByUserIdAndGatheringId(userId, gatheringId);
    }

    @Override
    public void deleteByUserIdAndGatheringId(Long userId, Long gatheringId) {
        gatheringMemberJpaRepository.deleteByUserIdAndGatheringId(userId, gatheringId);
    }
}
