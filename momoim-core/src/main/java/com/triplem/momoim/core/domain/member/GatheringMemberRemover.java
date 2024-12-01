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
}
