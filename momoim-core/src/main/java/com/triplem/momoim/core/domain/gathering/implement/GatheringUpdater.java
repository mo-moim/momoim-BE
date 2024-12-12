package com.triplem.momoim.core.domain.gathering.implement;

import com.triplem.momoim.core.domain.gathering.dto.ModifyGathering;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringUpdater {
    private final GatheringRepository gatheringRepository;

    public void cancelGathering(Long managerId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);

        if (!gathering.isManager(managerId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_GATHERING);
        }

        gathering.cancel();
        gatheringRepository.save(gathering);
    }

    public void modifyGathering(Long managerId, ModifyGathering modifyGathering) {
        Gathering gathering = gatheringRepository.findById(modifyGathering.getGatheringId());

        if (!gathering.isManager(managerId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_GATHERING);
        }

        gathering.modify(modifyGathering);
        gatheringRepository.save(gathering);
    }
}
