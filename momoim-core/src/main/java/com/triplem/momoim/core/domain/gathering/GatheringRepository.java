package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    GatheringDetail getGatheringDetail(Long gatheringId, Long userId);

    List<GatheringPreview> getGatheringPreviews(GatheringSearchOption searchOption);

    List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation);
}
