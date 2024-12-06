package com.triplem.momoim.core.domain.member;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
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
            throw new BusinessException(ExceptionCode.FULL_PARTICIPANT_GATHERING);
        }

        if (gatheringMemberRepository.isGatheringMember(userId, gatheringId)) {
            throw new BusinessException(ExceptionCode.ALREADY_JOINED_GATHERING);
        }

        gatheringMemberRepository.save(GatheringMember.create(userId, gatheringId));

        gathering.increaseParticipantCount();
        gatheringRepository.save(gathering);
    }

}
