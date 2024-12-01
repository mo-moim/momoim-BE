package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.member.GatheringMemberAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringManagementService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberAppender gatheringMemberAppender;

    @Transactional
    public Gathering register(Gathering gathering) {
        Gathering savedGathering = gatheringRepository.save(gathering);
        gatheringMemberAppender.append(savedGathering.getManagerId(), savedGathering.getId());
        return savedGathering;
    }

    public void cancel(Long requesterId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);
        gathering.cancel(requesterId);
        gatheringRepository.save(gathering);
    }
}
