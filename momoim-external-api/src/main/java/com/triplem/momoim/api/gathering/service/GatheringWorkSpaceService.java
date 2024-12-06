package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.ModifyGathering;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.implement.GatheringMemberAppender;
import com.triplem.momoim.core.domain.member.implement.GatheringMemberRemover;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringWorkSpaceService {
    private final GatheringReader gatheringReader;
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRemover gatheringMemberRemover;
    private final GatheringMemberAppender gatheringMemberAppender;

    @Transactional
    public Gathering register(Gathering gathering) {
        Gathering savedGathering = gatheringRepository.save(gathering);
        gatheringMemberAppender.append(savedGathering.getManagerId(), savedGathering.getId());
        return savedGathering;
    }

    public List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringReader.getMyMageGatherings(userId, paginationInformation);
    }

    public void cancel(Long requesterId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);

        if (!gathering.isManager(requesterId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_GATHERING);
        }

        gathering.cancel();
        gatheringRepository.save(gathering);
    }

    public void modify(Long requesterId, ModifyGathering modifyGathering) {
        Gathering gathering = gatheringRepository.findById(modifyGathering.getGatheringId());

        if (!gathering.isManager(requesterId)) {
            throw new BusinessException(ExceptionCode.FORBIDDEN_GATHERING);
        }

        gathering.modify(modifyGathering);
        gatheringRepository.save(gathering);
    }

    public void kickMember(Long requesterId, Long kickMemberId) {
        gatheringMemberRemover.kickMember(requesterId, kickMemberId);
    }
}
