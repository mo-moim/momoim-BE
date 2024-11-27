package com.triplem.momoim.api.gathering;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRegister;
import com.triplem.momoim.core.domain.member.GatheringMemberAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringRegisterService {
    private final GatheringRegister gatheringRegister;
    private final GatheringMemberAppender gatheringMemberAppender;

    public Gathering register(Gathering gathering) {
        Gathering savedGathering = gatheringRegister.register(gathering);
        gatheringMemberAppender.append(savedGathering.getManagerId(), savedGathering.getId());
        return savedGathering;
    }
}
