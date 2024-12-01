package com.triplem.momoim.api.gathering;

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

        if (gathering.isEnd()) {
            throw new RuntimeException("종료 된 모임입니다.");
        }

        gatheringMemberRemover.removeGatheringMember(userId, gatheringId);
    }
}
