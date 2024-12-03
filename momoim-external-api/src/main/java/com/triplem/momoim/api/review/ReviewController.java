package com.triplem.momoim.api.review;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.common.DefaultApiResponse;
import com.triplem.momoim.core.domain.review.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 작성", summary = "모임 리뷰 작성", tags = {"reviews"}, description = "모임 리뷰 작성")
    public ApiResponse<Review> registerReview(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @RequestBody RegisterReviewRequest request) {
        Review review = reviewService.register(request.toModel(userId));
        return ApiResponse.success(review);
    }

    @PatchMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 수정", summary = "모임 리뷰 수정", tags = {"reviews"}, description = "모임 리뷰 수정")
    public DefaultApiResponse modifyReview(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @RequestBody ModifyReviewRequest request) {
        reviewService.modify(request.toModel(userId));
        return DefaultApiResponse.success();
    }

    @DeleteMapping("/{reviewId}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "모임 리뷰 삭제", summary = "모임 리뷰 삭제", tags = {"reviews"}, description = "모임 리뷰 삭제")
    public DefaultApiResponse deleteReview(
        @Parameter(name = "userId", description = "인증 설계 완료 전까지 임시로 사용하는 userId") @RequestParam Long userId,
        @Parameter(name = "reviewId", description = "삭제 할 리뷰 ID") @PathVariable Long reviewId) {
        reviewService.delete(userId, reviewId);
        return DefaultApiResponse.success();
    }
}
