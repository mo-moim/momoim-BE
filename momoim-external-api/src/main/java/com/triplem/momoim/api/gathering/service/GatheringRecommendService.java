package com.triplem.momoim.api.gathering.service;

import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.implement.GatheringReader;
import com.triplem.momoim.core.domain.user.UserInterestCategory;
import com.triplem.momoim.core.domain.user.UserInterestCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringRecommendService {
    private final GatheringReader gatheringReader;
    private final UserInterestCategoryRepository userInterestCategoryRepository;

    public List<GatheringPreview> getRecommendGatherings(Long userId, GatheringSearchOption option) {
        List<String> userInterestCategories = userInterestCategoryRepository.findAllByUserId(userId)
            .stream()
            .map(UserInterestCategory::getSubCategory)
            .toList();
        option.applyUserInterestSubcategories(userInterestCategories);
        
        return gatheringReader.searchGatherings(userId, option);
    }
}
