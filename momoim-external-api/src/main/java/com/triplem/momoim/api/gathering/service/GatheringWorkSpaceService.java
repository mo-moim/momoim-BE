package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.ModifyGathering;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.gathering.implement.GatheringRegister;
import com.triplem.momoim.core.domain.gathering.implement.GatheringUpdater;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.implement.GatheringMemberRemover;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringWorkSpaceService {
    private final GatheringReader gatheringReader;
    private final GatheringRegister gatheringRegister;
    private final GatheringUpdater gatheringUpdater;
    private final GatheringMemberRemover gatheringMemberRemover;

    @Transactional
    public Gathering register(Gathering gathering) {
        return gatheringRegister.register(gathering);
    }

    public List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringReader.getMyMageGatherings(userId, paginationInformation);
    }

    public void cancel(Long managerId, Long gatheringId) {
        gatheringUpdater.cancelGathering(managerId, gatheringId);
    }

    public void modify(Long requesterId, ModifyGathering modifyGathering) {
        gatheringUpdater.modifyGathering(requesterId, modifyGathering);
    }

    public void kickMember(Long requesterId, Long kickMemberId) {
        gatheringMemberRemover.kickMember(requesterId, kickMemberId);
    }
}
