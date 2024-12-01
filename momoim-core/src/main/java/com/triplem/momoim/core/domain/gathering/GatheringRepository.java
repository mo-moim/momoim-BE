package com.triplem.momoim.core.domain.gathering;

public interface GatheringRepository {
    Gathering save(Gathering gathering);

    Gathering findById(Long id);
}
