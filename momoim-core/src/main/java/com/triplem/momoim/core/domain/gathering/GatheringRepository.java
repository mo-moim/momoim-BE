package com.triplem.momoim.core.domain.gathering;

import java.util.List;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);

    List<Gathering> findBySearchOption(GatheringSearchOption searchOption);

    List<Gathering> getMyGatherings(Long userId, int offset, int limit);
}
