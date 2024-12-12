package com.triplem.momoim.core.domain.wishlist.implement;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.wishlist.infrastructure.WishlistRepository;
import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WishlistAppender {
    private final WishlistRepository wishlistRepository;
    private final GatheringRepository gatheringRepository;

    @Transactional
    public void appendWishlist(Long userId, Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId);

        if (gathering.isEnd()) {
            throw new BusinessException(ExceptionCode.UNAVAILABLE_GATHERING);
        }

        if (wishlistRepository.isAlreadyAppended(userId, gatheringId)) {
            throw new BusinessException(ExceptionCode.ALREADY_REGISTERED_WISHLIST);
        }

        wishlistRepository.save(Wishlist.create(userId, gatheringId));

        gathering.increaseWishlistCount();
        gatheringRepository.save(gathering);
    }
}
