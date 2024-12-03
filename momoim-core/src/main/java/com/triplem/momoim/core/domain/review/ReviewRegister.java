package com.triplem.momoim.core.domain.review;

import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewRegister {
    private final ReviewRepository reviewRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public Review review(Review review) {
        if (!gatheringMemberRepository.isGatheringMember(review.getUserId(), review.getGatheringId())) {
            throw new RuntimeException("모임 멤버만 리뷰를 작성할 수 있습니다.");
        }

        if (reviewRepository.isWrittenReview(review.getUserId(), review.getGatheringId())) {
            throw new RuntimeException("이미 리뷰를 작성한 모임입니다.");
        }

        return reviewRepository.save(review);
    }
}
