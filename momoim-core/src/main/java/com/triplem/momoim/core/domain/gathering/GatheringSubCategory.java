package com.triplem.momoim.core.domain.gathering;

import lombok.Getter;

@Getter
public enum GatheringSubCategory {
    MOVIE(GatheringCategory.CULTURE), CONCEPT(GatheringCategory.CULTURE), EXHIBITION(GatheringCategory.CULTURE),

    RESTAURANT(GatheringCategory.FOOD), COOKING(GatheringCategory.FOOD),

    SOCCER(GatheringCategory.SPORTS), BASKETBALL(GatheringCategory.SPORTS), FUTSAL(GatheringCategory.SPORTS), BIKE(GatheringCategory.SPORTS),

    PET(GatheringCategory.HOBBY),

    HIKING(GatheringCategory.TRAVEL), FISHING(GatheringCategory.TRAVEL), CAMPING(GatheringCategory.TRAVEL),

    LANGUAGE(GatheringCategory.STUDY), FINANCE(GatheringCategory.STUDY), SELFDEVELOP(GatheringCategory.STUDY),

    LOVE(GatheringCategory.MEETING), COFFEECHAT(GatheringCategory.MEETING);
    private final GatheringCategory parentCategory;

    GatheringSubCategory(GatheringCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
