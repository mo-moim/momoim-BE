package com.triplem.momoim.core.domain.gathering.enums;

import lombok.Getter;

@Getter
public enum GatheringSubCategory {
    MOVIE(GatheringCategory.CULTURE), CONCERT(GatheringCategory.CULTURE), EXHIBITION(GatheringCategory.CULTURE),
    PERFORMANCE(GatheringCategory.CULTURE),

    RESTAURANT(GatheringCategory.FOOD), COOKING(GatheringCategory.FOOD), BAKING(GatheringCategory.FOOD),

    SOCCER(GatheringCategory.SPORTS), BASKETBALL(GatheringCategory.SPORTS), BASEBALL(GatheringCategory.SPORTS), BIKE(GatheringCategory.SPORTS),
    GOLF(GatheringCategory.SPORTS), RUNNING(GatheringCategory.SPORTS), FITNESS(GatheringCategory.SPORTS), DIET(GatheringCategory.SPORTS),
    YOGA(GatheringCategory.SPORTS), PILATES(GatheringCategory.SPORTS), SWIMMING(GatheringCategory.SPORTS),

    PET(GatheringCategory.HOBBY), BOOK(GatheringCategory.HOBBY), MUSIC(GatheringCategory.HOBBY), PHOTO(GatheringCategory.HOBBY),
    GAME(GatheringCategory.HOBBY), DANCE(GatheringCategory.HOBBY), CRAFTING(GatheringCategory.HOBBY), MEDIA(GatheringCategory.HOBBY),
    SUB_CULTURE(GatheringCategory.HOBBY),

    HIKING(GatheringCategory.TRAVEL), FISHING(GatheringCategory.TRAVEL), CAMPING(GatheringCategory.TRAVEL), DOMESTIC(GatheringCategory.TRAVEL),
    INTERNATIONAL(GatheringCategory.TRAVEL),

    LANGUAGE(GatheringCategory.STUDY), FINANCE(GatheringCategory.STUDY), SELF_DEVELOP(GatheringCategory.STUDY),

    LOVE(GatheringCategory.MEETING), COFFEE_CHAT(GatheringCategory.MEETING), INTERVIEW(GatheringCategory.MEETING);
    private final GatheringCategory parentCategory;

    GatheringSubCategory(GatheringCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
