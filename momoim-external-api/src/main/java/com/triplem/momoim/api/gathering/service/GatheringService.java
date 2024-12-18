package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringReader gatheringReader;

    public List<GatheringPreview> searchGatherings(Long userId, GatheringSearchOption searchOption) {
        return gatheringReader.searchGatherings(userId, searchOption);
    }

    public GatheringDetail getGatheringDetail(Long gatheringId, Long userId) {
        return gatheringReader.getGatheringDetail(gatheringId, userId);
    }

    public List<GatheringCategory> getCategory() {
        return new ArrayList<>(Arrays.asList(GatheringCategory.values()));
    }

    public List<GatheringSubCategory> getSubCategory() {
        return new ArrayList<>(Arrays.asList(GatheringSubCategory.values()));
    }
}
