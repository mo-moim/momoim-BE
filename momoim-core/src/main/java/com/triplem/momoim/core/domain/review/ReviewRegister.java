package com.triplem.momoim.core.domain.review;

import com.triplem.momoim.core.domain.member.infrastructure.GatheringMemberRepository;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewRegister {
    private final ReviewRepository reviewRepository;
    private final GatheringMemberRepository gatheringMemberRepository;

    public Review review(Review review) {
        if (!gatheringMemberRepository.isGatheringMember(review.getUserId(), review.getGatheringId())) {
            throw new BusinessException(ExceptionCode.NOT_GATHERING_MEMBER);
        }

        if (reviewRepository.isWrittenReview(review.getUserId(), review.getGatheringId())) {
            throw new BusinessException(ExceptionCode.ALREADY_REVIEWED);
        }

        return reviewRepository.save(review);
    }
}
