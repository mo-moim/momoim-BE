package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GatheringSearchOption {
    private List<Long> gatheringIds;
    private String category;
    private String subCategory;
    private PaginationInformation paginationInformation;

    public static GatheringSearchOption of(List<Long> gatheringIds, GatheringCategory category, GatheringSubCategory subCategory,
        int offset, int limit) {
        return new GatheringSearchOption(
            gatheringIds,
            category != null ? category.name() : null,
            subCategory != null ? subCategory.name() : null,
            new PaginationInformation(offset, limit)
        );
    }
}
