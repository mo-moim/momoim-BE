package com.triplem.momoim.api.review.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.api.review.request.ModifyReviewRequest;
import com.triplem.momoim.api.review.request.RegisterReviewRequest;
import com.triplem.momoim.api.review.service.ReviewService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.GatheringPreview;
import com.triplem.momoim.core.domain.review.MyReview;
import com.triplem.momoim.core.domain.review.Review;
import com.triplem.momoim.core.domain.review.ReviewDetail;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "작성한 리뷰 목록 조회", summary = "작성한 리뷰 목록 조회", tags = {"reviews"}, description = "작성한 리뷰 목록 조회")
    public ApiResponse<List<MyReview>> me(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<MyReview> myReviews = reviewService.getMyReviews(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(myReviews);
    }

    @GetMapping("/un-review")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "리뷰 작성 가능한 모임 조회", summary = "리뷰 작성 가능한 모임 조회", tags = {"reviews"}, description = "리뷰 작성 가능한 모임 조회")
    public ApiResponse<List<GatheringPreview>> getUnReviewGatherings(
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringPreview> unReviewGatherings = reviewService.getUnReviewGatherings(userId, new PaginationInformation(offset, limit));
        return ApiResponse.success(unReviewGatherings);
    }

    @GetMapping("/{gatheringId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 조회", summary = "모임 리뷰 조회", tags = {"reviews"}, description = "모임 리뷰 조회")
    public ApiResponse<List<ReviewDetail>> gatherReview(
        @PathVariable Long gatheringId,
        @Parameter(description = "페이징 offset") @RequestParam int offset,
        @Parameter(description = "페이징 limit") @RequestParam int limit) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        return ApiResponse.success(reviewService.getReviews(gatheringId, userId, new PaginationInformation(offset, limit)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 작성", summary = "모임 리뷰 작성", tags = {"reviews"}, description = "모임 리뷰 작성")
    public ApiResponse<Review> registerReview(
        @RequestBody RegisterReviewRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        Review review = reviewService.register(request.toModel(userId));
        return ApiResponse.success(review);
    }

    @PatchMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 수정", summary = "모임 리뷰 수정", tags = {"reviews"}, description = "모임 리뷰 수정")
    public DefaultApiResponse modifyReview(
        @Parameter(description = "수정 할 리뷰 ID") @PathVariable Long reviewId,
        @RequestBody ModifyReviewRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        reviewService.modify(request.toModel(reviewId, userId));
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 삭제", summary = "모임 리뷰 삭제", tags = {"reviews"}, description = "모임 리뷰 삭제")
    public DefaultApiResponse deleteReview(
        @Parameter(description = "삭제 할 리뷰 ID") @PathVariable Long reviewId) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        reviewService.delete(userId, reviewId);
        return DefaultApiResponse.success();
    }
}
