package com.triplem.momoim.core.domain.gathering.infrastructure;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    GatheringDetail getGatheringDetail(Long gatheringId, Long userId);

    List<GatheringPreview> searchGatherings(GatheringSearchOption searchOption);

    List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation);

    List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation);

    List<GatheringPreview> getGatheringPreviews(List<Long> ids);
}
