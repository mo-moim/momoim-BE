package com.triplem.momoim.api.gathering.dto;

import com.triplem.momoim.core.domain.gathering.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GatheringCategoryInformation {
    private List<GatheringCategory> categories;
    private List<GatheringSubCategoryDetail> subCategories;

    public GatheringCategoryInformation(List<GatheringCategory> categories, List<GatheringSubCategory> subCategories) {
        this.categories = categories;
        this.subCategories = subCategories.stream()
            .map(subCategory -> new GatheringSubCategoryDetail(subCategory, subCategory.getParentCategory()))
            .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class GatheringSubCategoryDetail {
        private GatheringSubCategory name;
        private GatheringCategory parentCategory;
    }
}
