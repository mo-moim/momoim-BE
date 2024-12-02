package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.member.GatheringMemberAppender;
import com.triplem.momoim.core.domain.member.GatheringMemberRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringJoinService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberAppender gatheringMemberAppender;
    private final GatheringMemberRemover gatheringMemberRemover;

    public void joinGathering(Long userId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);
        gathering.validateJoin();

        gatheringMemberAppender.append(userId, gatheringId);
    }

    public void cancelJoinGathering(Long userId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);

        if (gathering.getManagerId().equals(userId)) {
            throw new RuntimeException("방장은 모임 취소를 할 수 없습니다.");
        }
        
        gatheringMemberRemover.removeGatheringMember(userId, gatheringId);
    }
}
