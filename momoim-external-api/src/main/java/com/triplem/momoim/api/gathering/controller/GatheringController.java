package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.gathering.dto.GatheringCategoryInformation;
import com.triplem.momoim.api.gathering.dto.GatheringDetail;
import com.triplem.momoim.api.gathering.dto.GatheringListItem;
import com.triplem.momoim.api.gathering.request.ModifyGatheringRequest;
import com.triplem.momoim.api.gathering.request.RegisterGatheringRequest;
import com.triplem.momoim.api.gathering.service.GatheringJoinService;
import com.triplem.momoim.api.gathering.service.GatheringManagementService;
import com.triplem.momoim.api.gathering.service.GatheringService;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.GatheringSubCategory;
import com.triplem.momoim.core.domain.member.GatheringMemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<List<GatheringListItem>> getGatherings(
        @Parameter(name = "모임 ID 리스트") @RequestParam(required = false) List<Long> ids,
        @Parameter(name = "메인 카테고리") @RequestParam(required = false) GatheringCategory category,
        @Parameter(name = "서브 카테고리") @RequestParam(required = false) GatheringSubCategory subCategory,
        @Parameter(name = "페이징 offset") @RequestParam int offset,
        @Parameter(name = "페이징 limit") @RequestParam int limit) {
        List<GatheringListItem> gatherings = gatheringService.searchGathering(
            GatheringSearchOption.of(ids, category, subCategory, offset, limit));
        return ApiResponse.success(gatherings);
    }

    @PostMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 생성", summary = "모임 생성", tags = {"gatherings"}, description = "모임 생성")
    public ApiResponse<GatheringListItem> registerGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @RequestBody RegisterGatheringRequest request) {
        Gathering gathering = gatheringManagementService.register(request.toGathering(userId));
        return ApiResponse.success(GatheringListItem.from(gathering));
    }

    @GetMapping("/joined")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "참여중인 모임 목록 조회", summary = "참여중인 모임 목록 조회", tags = {"gatherings"}, description = "참여중인 모임 목록 조회")
    public ApiResponse<List<GatheringListItem>> getMyGatherings(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "페이징 offset") @RequestParam int offset,
        @Parameter(name = "페이징 limit") @RequestParam int limit) {
        List<GatheringListItem> myGatherings = gatheringService.getMyGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(myGatherings);
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
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam(defaultValue = "-1") Long userId,
        @Parameter(name = "조회 할 모임 ID") @PathVariable Long gatheringId) {
        GatheringDetail gathering = gatheringService.getGatheringDetail(gatheringId, userId);
        return ApiResponse.success(gathering);
    }

    @GetMapping("/{gatheringId}/participants")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 멤버 조회", summary = "모임 멤버 조회", tags = {"gatherings"}, description = "모임 멤버 조회")
    public ApiResponse<List<GatheringMemberDetail>> getGatheringMembers(
        @Parameter(name = "조회 할 모임 ID") @PathVariable Long gatheringId) {
        List<GatheringMemberDetail> members = gatheringService.getGatheringMembers(gatheringId);
        return ApiResponse.success(members);
    }

    @PutMapping("/{gatheringId}/cancel")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 취소", summary = "모임 취소", tags = {"gatherings"}, description = "모임 취소")
    public DefaultApiResponse cancelGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @PathVariable Long gatheringId) {
        gatheringManagementService.cancel(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @PostMapping("/{gatheringId}/join")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 참여", summary = "모임 참여", tags = {"gatherings"}, description = "모임 참여")
    public DefaultApiResponse joinGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "모임 ID") @PathVariable Long gatheringId) {
        gatheringJoinService.joinGathering(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/{gatheringId}/leave")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 참여 취소", summary = "모임 참여 취소", tags = {"gatherings"}, description = "모임 참여 취소")
    public DefaultApiResponse leaveGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "모임 ID") @PathVariable Long gatheringId) {
        gatheringJoinService.cancelJoinGathering(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @PatchMapping("/{gatheringId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 수정", summary = "모임 수정", tags = {"gatherings"}, description = "모임 수정")
    public DefaultApiResponse modifyGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "모임 ID") @PathVariable Long gatheringId,
        @RequestBody ModifyGatheringRequest request) {
        gatheringManagementService.modify(userId, request.toContent(gatheringId));
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/member/{gatheringMemberId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "멤버 추방", summary = "멤버 추방", tags = {"gatherings"}, description = "멤버 추방")
    public DefaultApiResponse kickMember(
        @Parameter(name = "추방 할 gatheringMemberId") @PathVariable Long gatheringMemberId,
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId) {
        gatheringManagementService.kickMember(userId, gatheringMemberId);
        return DefaultApiResponse.success();
    }
}
