package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.api.gathering.dto.GatheringDetail;
import com.triplem.momoim.api.gathering.dto.GatheringListItem;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public List<GatheringListItem> searchGathering(GatheringSearchOption searchOption) {
        return gatheringRepository.findBySearchOption(searchOption)
            .stream()
            .map(GatheringListItem::from)
            .collect(Collectors.toList());
    }

    public GatheringDetail getGatheringDetail(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);
        return GatheringDetail.from(gathering);
    }

    public List<GatheringMemberDetail> getGatheringMembers(Long gatheringId) {
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gatheringId);

        if (members.isEmpty()) {
            throw new RuntimeException("모임을 찾을 수 없습니다.");
        }

        return members;
    }

    public List<GatheringListItem> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyGatherings(userId, paginationInformation)
            .stream()
            .map(GatheringListItem::from)
            .collect(Collectors.toList());
    }

    public List<GatheringCategory> getCategory() {
        return new ArrayList<>(Arrays.asList(GatheringCategory.values()));
    }

    public List<GatheringSubCategory> getSubCategory() {
        return new ArrayList<>(Arrays.asList(GatheringSubCategory.values()));
    }
}
