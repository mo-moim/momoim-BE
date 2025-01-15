package com.triplem.momoim.api.gathering.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.gathering.service.GatheringJoinService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatherings/join")
@RequiredArgsConstructor
public class GatheringJoinController {
    private final GatheringJoinService gatheringJoinService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "내가 속한 모임 조회", summary = "내가 속한 모임 조회", tags = {"gatherings"}, description = "내가 속한 모임 조회")
    public ApiResponse<List<GatheringPreview>> getMyGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> myGatherings = gatheringJoinService.getMyGatherings(
            userId,
            new PaginationInformation(offset, limit)
        );
        return ApiResponse.success(myGatherings);
    }

    @PostMapping("/{gatheringId}")
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

    @DeleteMapping("/{gatheringId}")
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
}
