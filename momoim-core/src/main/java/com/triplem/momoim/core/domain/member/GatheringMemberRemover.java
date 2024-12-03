package com.triplem.momoim.core.domain.member;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GatheringMemberRemover {
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringRepository gatheringRepository;

    @Transactional
    public void removeGatheringMember(Long userId, Long gatheringId) {
        if (!gatheringMemberRepository.isGatheringMember(userId, gatheringId)) {
            throw new RuntimeException("참여중인 모임이 아닙니다.");
        }

        Gathering gathering = gatheringRepository.findById(gatheringId);

        gathering.decreaseParticipantCount();
        gatheringRepository.save(gathering);

        gatheringMemberRepository.deleteByUserIdAndGatheringId(userId, gatheringId);
    }

    public void kickMember(Long managerId, Long kickMemberId) {
        GatheringMember kickMember = gatheringMemberRepository.findById(kickMemberId);
        Gathering gathering = gatheringRepository.findById(kickMember.getGatheringId());

        if (!gathering.isManager(managerId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        if (!managerId.equals(kickMemberId)) {
            throw new RuntimeException("자신을 강퇴할 수 없습니다.");
        }

        gatheringMemberRepository.deleteById(kickMemberId);

        gathering.decreaseParticipantCount();
        gatheringRepository.save(gathering);
    }
}
