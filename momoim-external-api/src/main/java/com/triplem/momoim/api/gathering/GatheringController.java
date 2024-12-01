package com.triplem.momoim.api.gathering;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.core.domain.gathering.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ApiResponse<List<GatheringItem>> getGatherings(
        @Parameter(name = "모임 ID 리스트") @RequestParam(required = false) List<Long> ids,
        @Parameter(name = "메인 카테고리") @RequestParam(required = false) GatheringCategory category,
        @Parameter(name = "서브 카테고리") @RequestParam(required = false) GatheringSubCategory subCategory,
        @Parameter(name = "페이징 offset") @RequestParam int offset,
        @Parameter(name = "페이징 limit") @RequestParam int limit) {
        List<GatheringItem> gatherings = gatheringService.searchGathering(
            GatheringSearchOption.of(ids, category, subCategory, offset, limit));
        return ApiResponse.success(gatherings);
    }
}
