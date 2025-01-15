package com.triplem.momoim.core.domain.wishlist.infrastructure;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import java.util.List;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);

    List<Wishlist> getMyWishlist(Long userId, PaginationInformation paginationInformation);

    Boolean isAlreadyAppended(Long userId, Long gatheringId);

    Wishlist findByUserIdAndGatheringId(Long userId, Long gatheringId);

    void deleteById(Long id);
}