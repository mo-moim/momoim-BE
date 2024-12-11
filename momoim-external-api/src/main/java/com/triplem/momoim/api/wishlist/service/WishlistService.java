package com.triplem.momoim.api.wishlist.service;

import com.triplem.momoim.core.domain.wishlist.implement.WishlistAppender;
import com.triplem.momoim.core.domain.wishlist.implement.WishlistRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistAppender wishlistAppender;
    private final WishlistRemover wishlistRemover;

    public void appendWishlist(Long userId, Long gatheringId) {
        wishlistAppender.appendWishlist(userId, gatheringId);
    }

    public void removeWishlist(Long userId, Long gatheringId) {
        wishlistRemover.removeWishlist(userId, gatheringId);
    }
}
