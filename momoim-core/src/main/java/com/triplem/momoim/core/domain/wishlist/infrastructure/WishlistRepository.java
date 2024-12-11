package com.triplem.momoim.core.domain.wishlist.infrastructure;

import com.triplem.momoim.core.domain.wishlist.model.Wishlist;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);

    Boolean isAlreadyAppended(Long userId, Long gatheringId);
}
