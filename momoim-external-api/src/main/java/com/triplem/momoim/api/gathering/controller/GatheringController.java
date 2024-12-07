package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.gathering.dto.GatheringCategoryInformation;
import com.triplem.momoim.api.gathering.service.GatheringService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatherings")
@RequiredArgsConstructor
public class GatheringController {
    private final GatheringService gatheringService;

    @GetMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 목록 조회", summary = "모임 목록 조회", tags = {"gatherings"}, description = "모임 목록 조회")
    @Parameters({
        @Parameter(name = "gatheringIds", description = "필터링 모임 id 목록", example = "1,2,3", schema = @Schema(implementation = Long[].class)),
        @Parameter(name = "category", description = "필터링 카테고리", schema = @Schema(implementation = GatheringCategory.class)),
        @Parameter(name = "subCategory", description = "필터링 서브 카테고리", schema = @Schema(implementation = GatheringSubCategory.class)),
        @Parameter(name = "location", description = "필터링 지역", schema = @Schema(implementation = GatheringLocation.class)),
        @Parameter(name = "gatheringDate", description = "필터링 모임 날짜", example = "2024-12-31", schema = @Schema(implementation = LocalDate.class)),
        @Parameter(name = "offset", description = "페이징 offset", schema = @Schema(implementation = Integer.class), required = true),
        @Parameter(name = "limit", description = "페이징 limit", schema = @Schema(implementation = Integer.class), required = true),
        @Parameter(name = "sortType", description = "정렬 기준", schema = @Schema(implementation = GatheringSortType.class), required = true),
        @Parameter(name = "sortOrder", description = "오름차순 / 내림차순", schema = @Schema(implementation = SortOrder.class), required = true),
    })
    public ApiResponse<List<GatheringPreview>> getGatherings(
        @Parameter(hidden = true) GatheringSearchOption option) {
        List<GatheringPreview> gatherings = gatheringService.searchGatherings(option);
        return ApiResponse.success(gatherings);
    }

    @GetMapping("/categories")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 카테고리 정보 조회", summary = "모임 카테고리 정보 조회", tags = {"gatherings"}, description = "모임 카테고리 정보 조회")
    public ApiResponse<GatheringCategoryInformation> getGatheringCategories() {
        List<GatheringCategory> categories = gatheringService.getCategory();
        List<GatheringSubCategory> subCategories = gatheringService.getSubCategory();
        return ApiResponse.success(new GatheringCategoryInformation(categories, subCategories));
    }

    @GetMapping("/{gatheringId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 상세 조회", summary = "모임 상세 조회", tags = {"gatherings"}, description = "모임 상세 조회")
    public ApiResponse<GatheringDetail> getGathering(
        @Parameter(description = "조회 할 모임 ID") @PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        GatheringDetail gathering = gatheringService.getGatheringDetail(gatheringId, userId);
        return ApiResponse.success(gathering);
    }
}
