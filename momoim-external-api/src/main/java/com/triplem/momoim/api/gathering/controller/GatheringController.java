package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.gathering.dto.GatheringItem;
import com.triplem.momoim.api.gathering.dto.RegisterGatheringRequest;
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

    @PostMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 생성", summary = "모임 생성", tags = {"gatherings"}, description = "모임 생성")
    public ApiResponse<GatheringItem> registerGathering(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @RequestBody RegisterGatheringRequest request) {
        Gathering gathering = gatheringManagementService.register(request.toGathering(userId));
        return ApiResponse.success(GatheringItem.from(gathering));
    }

    @GetMapping("/joined")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "참여중인 모임 목록 조회", summary = "참여중인 모임 목록 조회", tags = {"gatherings"}, description = "참여중인 모임 목록 조회")
    public ApiResponse<List<GatheringItem>> getMyGatherings(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "페이징 offset") @RequestParam int offset,
        @Parameter(name = "페이징 limit") @RequestParam int limit) {
        List<GatheringItem> myGatherings = gatheringService.getMyGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(myGatherings);
    }

    @GetMapping("/{gatheringId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 상세 조회", summary = "모임 상세 조회", tags = {"gatherings"}, description = "모임 상세 조회")
    public ApiResponse<GatheringItem> getGathering(
        @Parameter(name = "조회 할 모임 ID") @PathVariable Long gatheringId) {
        GatheringItem gathering = gatheringService.getGathering(gatheringId);
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
}
