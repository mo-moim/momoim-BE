package com.triplem.momoim.core.domain.gathering;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringRegister {
    private final GatheringRepository gatheringRepository;

    public Gathering register(Gathering gathering) {
        return gatheringRepository.save(gathering);
    }
}
