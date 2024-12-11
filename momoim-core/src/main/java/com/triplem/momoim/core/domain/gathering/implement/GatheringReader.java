package com.triplem.momoim.core.domain.gathering.implement;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringContent;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.member.dto.GatheringMemberDetail;
import com.triplem.momoim.core.domain.member.infrastructure.GatheringMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringReader {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public Gathering getById(Long gatheringId) {
        return gatheringRepository.findById(gatheringId);
    }

    public List<GatheringPreview> searchGatherings(Long userId, GatheringSearchOption option) {
        return gatheringRepository.searchGatherings(userId, option);
    }

    public GatheringDetail getGatheringDetail(Long gatheringId, Long userId) {
        GatheringContent gatheringContent = gatheringRepository.getGatheringContent(gatheringId);
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gatheringId);
        Boolean isManager = gatheringContent.getManagerId().equals(userId);
        Boolean isJoined = members
            .stream()
            .anyMatch(member -> member.getUserId().equals(userId));
        return new GatheringDetail(gatheringContent, members, isJoined, isManager);
    }

    public List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyMadeGatherings(userId, paginationInformation);
    }

    public List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return gatheringRepository.getMyGatherings(userId, paginationInformation);
    }

    public List<GatheringPreview> getGatheringPreviewsById(Long userId, List<Long> ids) {
        return gatheringRepository.getGatheringPreviewsById(userId, ids);
    }
}
