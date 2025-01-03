package com.triplem.momoim.core.domain.gathering.implement;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringStatusScheduler {
    private final GatheringRepository gatheringRepository;

    @Scheduled(cron = "0 * * * * *")
    public void updateGatheringStatusFinished() {
        gatheringRepository.updateGatheringStatusFinished();
    }
}
