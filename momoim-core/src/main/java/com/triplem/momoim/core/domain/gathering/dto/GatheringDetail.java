package com.triplem.momoim.core.domain.gathering.dto;

import com.triplem.momoim.core.domain.member.dto.GatheringMemberDetail;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GatheringDetail {
    private GatheringContent gatheringContent;
    private List<GatheringMemberDetail> members;
    private Boolean isJoined;
    private Boolean isManager;
}
