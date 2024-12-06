package com.triplem.momoim.core.domain.member.implement;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.infrastructure.GatheringMemberRepository;
import com.triplem.momoim.core.domain.member.model.GatheringMember;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
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
            throw new BusinessException(ExceptionCode.NOT_GATHERING_MEMBER);
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
            throw new BusinessException(ExceptionCode.FORBIDDEN_GATHERING);
        }

        if (!managerId.equals(kickMemberId)) {
            throw new BusinessException(ExceptionCode.UNAVAILABLE_MANAGER_LEAVE);
        }

        gatheringMemberRepository.deleteById(kickMemberId);

        gathering.decreaseParticipantCount();
        gatheringRepository.save(gathering);
    }
}
