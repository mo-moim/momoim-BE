package com.triplem.momoim.core.domain.gathering.implement;

import com.triplem.momoim.core.domain.gathering.dto.GatheringContent;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
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

    public List<GatheringPreview> searchGatherings(GatheringSearchOption option) {
        return gatheringRepository.searchGatherings(option);
    }

    public GatheringDetail getGatheringDetail(Long gatheringId, Long userId) {
        GatheringContent gatheringContent = gatheringRepository.getGatheringContent(gatheringId);
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gatheringId);
        Boolean isManager = gatheringContent.getManagerId().equals(userId);
        Boolean isJoined = members
            .stream()
            .map(GatheringMemberDetail::getUserId)
            .filter(id -> id.equals(userId))
            .count() == 1;
        return new GatheringDetail(gatheringContent, members, isJoined, isManager);
    }
}
