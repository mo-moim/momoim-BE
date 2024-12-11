package com.triplem.momoim.api.wishlist.service;

import com.triplem.momoim.core.domain.wishlist.implement.WishlistAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistAppender wishlistAppender;

    public void appendWishlist(Long userId, Long gatheringId) {
        wishlistAppender.appendWishlist(userId, gatheringId);
    }
}
