package com.triplem.momoim.api.wishlist.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.wishlist.service.WishlistService;
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
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "찜 목록 조회", summary = "찜 목록 조회", tags = {"wishlist"}, description = "찜 목록 조회")
    public ApiResponse<List<GatheringPreview>> getMyWishlistGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> wishlistGatherings = wishlistService.getMyWishlistGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(wishlistGatherings);
    }

    @PostMapping("/{gatheringId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "찜 목록 추가", summary = "찜 목록 추가", tags = {"wishlist"}, description = "찜 목록 추가")
    public DefaultApiResponse appendWishlist(@PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        wishlistService.appendWishlist(userId, gatheringId);
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/{gatheringId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "찜 목록 삭제", summary = "찜 목록 삭제", tags = {"wishlist"}, description = "찜 목록 삭제")
    public DefaultApiResponse deleteWishlist(@PathVariable Long gatheringId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        wishlistService.removeWishlist(userId, gatheringId);
        return DefaultApiResponse.success();
    }
}
