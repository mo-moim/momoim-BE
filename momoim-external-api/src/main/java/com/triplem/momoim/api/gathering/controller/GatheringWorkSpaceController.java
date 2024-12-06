package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.gathering.request.ModifyGatheringRequest;
import com.triplem.momoim.api.gathering.request.RegisterGatheringRequest;
import com.triplem.momoim.api.gathering.response.RegisterGatheringResponse;
import com.triplem.momoim.api.gathering.service.GatheringWorkSpaceService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatherings/workspace")
@RequiredArgsConstructor
public class GatheringWorkSpaceController {
    private final GatheringWorkSpaceService gatheringWorkSpaceService;

    @GetMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "내가 만든 모임 조회", summary = "내가 만든 모임 조회", tags = {"gatherings-workspace"}, description = "내가 만든 모임 조회")
    public ApiResponse<List<GatheringPreview>> getMyMadeGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit
    ) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> myMadeGatherings = gatheringWorkSpaceService.getMyMadeGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(myMadeGatherings);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 생성", summary = "모임 생성", tags = {"gatherings-workspace"}, description = "모임 생성")
    public ApiResponse<RegisterGatheringResponse> registerGathering(
        @RequestBody RegisterGatheringRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        Gathering gathering = gatheringWorkSpaceService.register(request.toGathering(userId));
        return ApiResponse.success(new RegisterGatheringResponse(gathering.getId()));
    }

    @DeleteMapping("/{gatheringId}/cancel")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 취소", summary = "모임 취소", tags = {"gatherings-workspace"}, description = "모임 취소")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public DefaultApiResponse cancelGathering(
        @PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringWorkSpaceService.cancel(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @PatchMapping("/{gatheringId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 수정", summary = "모임 수정", tags = {"gatherings-workspace"}, description = "모임 수정")
    public DefaultApiResponse modifyGathering(
        @Parameter(description = "모임 ID") @PathVariable Long gatheringId,
        @RequestBody ModifyGatheringRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringWorkSpaceService.modify(userId, request.toContent(gatheringId));
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/member/{gatheringMemberId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "멤버 추방", summary = "멤버 추방", tags = {"gatherings-workspace"}, description = "멤버 추방")
    public DefaultApiResponse kickMember(
        @Parameter(description = "추방 할 gatheringMemberId") @PathVariable Long gatheringMemberId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        gatheringWorkSpaceService.kickMember(userId, gatheringMemberId);
        return DefaultApiResponse.success();
    }
}
