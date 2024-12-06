package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public List<GatheringPreview> searchGathering(GatheringSearchOption searchOption) {
        return gatheringRepository.searchGatherings(searchOption);
    }

    public GatheringDetail getGatheringDetail(Long gatheringId, Long userId) {
        return gatheringRepository.getGatheringDetail(gatheringId, userId);
    }

    public List<GatheringMemberDetail> getGatheringMembers(Long gatheringId) {
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gatheringId);

        if (members.isEmpty()) {
            throw new BusinessException(ExceptionCode.NOT_FOUND_GATHERING);
        }

        return members;
    }

    public List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyGatherings(userId, paginationInformation);
    }

    public List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyMadeGatherings(userId, paginationInformation);
    }

    public List<GatheringCategory> getCategory() {
        return new ArrayList<>(Arrays.asList(GatheringCategory.values()));
    }

    public List<GatheringSubCategory> getSubCategory() {
        return new ArrayList<>(Arrays.asList(GatheringSubCategory.values()));
    }
}
