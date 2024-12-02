package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    List<Gathering> findBySearchOption(GatheringSearchOption searchOption);

    List<Gathering> getMyGatherings(Long userId, PaginationInformation paginationInformation);
}
