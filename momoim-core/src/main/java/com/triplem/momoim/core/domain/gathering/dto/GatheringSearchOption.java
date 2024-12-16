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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private GatheringType gatheringType;
    private String category;
    private String subCategory;
    private GatheringLocation gatheringLocation;
    private LocalDate gatheringDate;
    private PaginationInformation paginationInformation;
    private GatheringSortType sortType;
    private SortOrder sortOrder;

    public static GatheringSearchOption of(
        List<Long> gatheringIds, GatheringType gatheringType, GatheringCategory category, GatheringSubCategory subCategory,
        GatheringLocation gatheringLocation,
        LocalDate gatheringDate, PaginationInformation paginationInformation, GatheringSortType sortType, SortOrder sortOrder) {
        return new GatheringSearchOption(
            gatheringIds,
            gatheringType,
            category != null ? category.name() : null,
            subCategory != null ? subCategory.name() : null,
            gatheringLocation,
            gatheringDate,
            paginationInformation,
            sortType,
            sortOrder
        );
    }
}
