package com.triplem.momoim.core.domain.wishlist.implement;

import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
import com.triplem.momoim.core.domain.wishlist.infrastructure.WishlistRepository;
import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WishlistRemover {
    private final WishlistRepository wishlistRepository;
    private final GatheringRepository gatheringRepository;

    @Transactional
    public void removeWishlist(Long userId, Long gatheringId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndGatheringId(userId, gatheringId);
        wishlistRepository.deleteById(wishlist.getId());

        Gathering gathering = gatheringRepository.findById(gatheringId);
        gathering.decreaseWishlistCount();
        gatheringRepository.save(gathering);
    }
}
