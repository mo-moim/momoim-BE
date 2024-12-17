package com.triplem.momoim.core.domain.gathering.dto;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private GatheringType gatheringType;
    private List<String> categories;
    private List<String> subCategories;
    private GatheringLocation gatheringLocation;
    private LocalDate gatheringDate;
    private PaginationInformation paginationInformation;
    private GatheringSortType sortType;
    private SortOrder sortOrder;

    public static GatheringSearchOption of(
        List<Long> gatheringIds, GatheringType gatheringType, List<GatheringCategory> categories, List<GatheringSubCategory> subCategories,
        GatheringLocation gatheringLocation, LocalDate gatheringDate, PaginationInformation paginationInformation, GatheringSortType sortType,
        SortOrder sortOrder) {
        return new GatheringSearchOption(
            gatheringIds,
            gatheringType,
            parseCategory(categories),
            parseSubCategory(subCategories),
            gatheringLocation,
            gatheringDate,
            paginationInformation,
            sortType,
            sortOrder
        );
    }

    private static List<String> parseCategory(List<GatheringCategory> categories) {
        return categories
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    private static List<String> parseSubCategory(List<GatheringSubCategory> subCategories) {
        return subCategories
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
    }
}
