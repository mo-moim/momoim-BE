package com.triplem.momoim.core.domain.gathering.infrastructure;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringContent;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    GatheringContent getGatheringContent(Long gatheringId);

    List<GatheringPreview> searchGatherings(GatheringSearchOption searchOption);

    List<GatheringPreview> getMyGatherings(Long userId, PaginationInformation paginationInformation);

    List<GatheringPreview> getMyMadeGatherings(Long userId, PaginationInformation paginationInformation);

    List<GatheringPreview> getGatheringPreviewsById(List<Long> ids);
}
