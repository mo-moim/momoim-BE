package com.triplem.momoim.core.domain.member;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringMemberAppender {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public void append(Long userId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);
        if (gathering.isFull()) {
            throw new RuntimeException("인원이 다 찬 모임입니다.");
        }

        if (gatheringMemberRepository.isGatheringMember(userId, gatheringId)) {
            throw new RuntimeException("이미 가입 한 모임입니다.");
        }

        gatheringMemberRepository.save(GatheringMember.create(userId, gatheringId));

        gathering.increaseParticipantCount();
        gatheringRepository.save(gathering);
    }

}
