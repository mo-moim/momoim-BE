package com.triplem.momoim.api.wishlist.controller;

import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.wishlist.service.WishlistService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

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
}
