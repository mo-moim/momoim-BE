package com.triplem.momoim.api.gathering;

import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;

    public List<GatheringItem> searchGathering(GatheringSearchOption searchOption) {
        return gatheringRepository.findBySearchOption(searchOption)
            .stream()
            .map(GatheringItem::from)
            .collect(Collectors.toList());
    }
}
