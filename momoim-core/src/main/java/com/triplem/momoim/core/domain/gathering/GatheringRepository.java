package com.triplem.momoim.core.domain.gathering;

import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    GatheringDetail getGatheringDetail(Long gatheringId, Long userId);

    List<GatheringPreview> searchGatherings(GatheringSearchOption searchOption);

    List<GatheringPreview> getMyGatherings(Long userId, MyGatheringOption option);
}
