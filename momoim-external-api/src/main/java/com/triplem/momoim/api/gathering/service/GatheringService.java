package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.api.gathering.dto.GatheringItem;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public List<GatheringItem> searchGathering(GatheringSearchOption searchOption) {
        return gatheringRepository.findBySearchOption(searchOption)
            .stream()
            .map(GatheringItem::from)
            .collect(Collectors.toList());
    }

    public GatheringItem getGathering(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);
        return GatheringItem.from(gathering);
    }

    public List<GatheringMemberDetail> getGatheringMembers(Long gatheringId) {
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gatheringId);

        if (members.isEmpty()) {
            throw new RuntimeException("모임을 찾을 수 없습니다.");
        }

        return members;
    }

    public List<GatheringItem> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyGatherings(userId, paginationInformation)
            .stream()
            .map(GatheringItem::from)
            .collect(Collectors.toList());
    }
}
