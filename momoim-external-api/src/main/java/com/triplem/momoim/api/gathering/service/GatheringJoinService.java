package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.implement.GatheringMemberAppender;
import com.triplem.momoim.core.domain.member.implement.GatheringMemberRemover;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringJoinService {
    private final GatheringReader gatheringReader;
    private final GatheringMemberAppender gatheringMemberAppender;
    private final GatheringMemberRemover gatheringMemberRemover;

    public void joinGathering(Long userId, Long gatheringId) {
        Gathering gathering = gatheringReader.getById(gatheringId);
        gathering.validateJoin();

        gatheringMemberAppender.append(userId, gatheringId);
    }

    public void cancelJoinGathering(Long userId, Long gatheringId) {
        Gathering gathering = gatheringReader.getById(gatheringId);

        if (gathering.getManagerId().equals(userId)) {
            throw new BusinessException(ExceptionCode.UNAVAILABLE_MANAGER_LEAVE);
        }

        gatheringMemberRemover.removeGatheringMember(userId, gatheringId);
    }
}
