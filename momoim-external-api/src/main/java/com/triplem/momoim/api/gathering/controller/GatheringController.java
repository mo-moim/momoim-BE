package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.gathering.dto.GatheringCategoryInformation;
import com.triplem.momoim.api.gathering.request.ModifyGatheringRequest;
import com.triplem.momoim.api.gathering.request.RegisterGatheringRequest;
import com.triplem.momoim.api.gathering.service.GatheringJoinService;
import com.triplem.momoim.api.gathering.service.GatheringManagementService;
import com.triplem.momoim.api.gathering.service.GatheringService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.dto.GatheringDetail;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatherings")
@RequiredArgsConstructor
public class GatheringController {
    private final GatheringService gatheringService;
    private final GatheringManagementService gatheringManagementService;
    private final GatheringJoinService gatheringJoinService;

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

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 생성", summary = "모임 생성", tags = {"gatherings"}, description = "모임 생성")
    //todo 응답으로 GatheringID만 주도록 변경하기
    public ApiResponse<GatheringPreview> registerGathering(
        @RequestBody RegisterGatheringRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        Gathering gathering = gatheringManagementService.register(request.toGathering(userId));
        return ApiResponse.success(GatheringPreview.from(gathering));
    }

    @GetMapping("/joined")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "내가 속한 모임 조회", summary = "내가 속한 모임 조회", tags = {"gatherings"}, description = "내가 속한 모임 조회")
    public ApiResponse<List<GatheringPreview>> getMyGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> myGatherings = gatheringService.getMyGatherings(
            userId,
            new PaginationInformation(offset, limit)
        );
        return ApiResponse.success(myGatherings);
    }

    @GetMapping("/made")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "내가 만든 모임 조회", summary = "내가 만든 모임 조회", tags = {"gatherings"}, description = "내가 만든 모임 조회")
    public ApiResponse<List<GatheringPreview>> getMyMadeGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit
    ) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> myMadeGatherings = gatheringService.getMyMadeGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(myMadeGatherings);
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

    @PutMapping("/{gatheringId}/cancel")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 취소", summary = "모임 취소", tags = {"gatherings"}, description = "모임 취소")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public DefaultApiResponse cancelGathering(
        @PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringManagementService.cancel(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @PostMapping("/{gatheringId}/join")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 참여", summary = "모임 참여", tags = {"gatherings"}, description = "모임 참여")
    public DefaultApiResponse joinGathering(
        @Parameter(description = "모임 ID") @PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringJoinService.joinGathering(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/{gatheringId}/leave")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 참여 취소", summary = "모임 참여 취소", tags = {"gatherings"}, description = "모임 참여 취소")
    public DefaultApiResponse leaveGathering(
        @Parameter(description = "모임 ID") @PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringJoinService.cancelJoinGathering(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @PatchMapping("/{gatheringId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 수정", summary = "모임 수정", tags = {"gatherings"}, description = "모임 수정")
    public DefaultApiResponse modifyGathering(
        @Parameter(description = "모임 ID") @PathVariable Long gatheringId,
        @RequestBody ModifyGatheringRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringManagementService.modify(userId, request.toContent(gatheringId));
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/member/{gatheringMemberId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "멤버 추방", summary = "멤버 추방", tags = {"gatherings"}, description = "멤버 추방")
    public DefaultApiResponse kickMember(
        @Parameter(description = "추방 할 gatheringMemberId") @PathVariable Long gatheringMemberId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringManagementService.kickMember(userId, gatheringMemberId);
        return DefaultApiResponse.success();
    }
}
