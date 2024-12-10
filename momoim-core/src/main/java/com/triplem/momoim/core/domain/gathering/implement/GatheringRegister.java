package com.triplem.momoim.core.domain.gathering.implement;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.infrastructure.GatheringMemberRepository;
import com.triplem.momoim.core.domain.member.model.GatheringMember;
import com.triplem.momoim.core.domain.review.infrastructure.ReviewStatisticRepository;
import com.triplem.momoim.core.domain.review.model.ReviewStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GatheringRegister {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final ReviewStatisticRepository reviewStatisticRepository;

    @Transactional
    public Gathering register(Gathering gathering) {
        Gathering savedGathering = gatheringRepository.save(gathering);
        GatheringMember manager = GatheringMember.create(gathering.getManagerId(), savedGathering.getId());
        gatheringMemberRepository.save(manager);
        reviewStatisticRepository.save(ReviewStatistic.create(savedGathering.getId()));
        return savedGathering;
    }
}
