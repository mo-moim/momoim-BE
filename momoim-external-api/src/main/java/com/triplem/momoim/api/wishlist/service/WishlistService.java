package com.triplem.momoim.api.wishlist.service;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.wishlist.implement.WishlistAppender;
import com.triplem.momoim.core.domain.wishlist.implement.WishlistReader;
import com.triplem.momoim.core.domain.wishlist.implement.WishlistRemover;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistAppender wishlistAppender;
    private final WishlistRemover wishlistRemover;
    private final WishlistReader wishlistReader;

    public void appendWishlist(Long userId, Long gatheringId) {
        wishlistAppender.appendWishlist(userId, gatheringId);
    }

    public void removeWishlist(Long userId, Long gatheringId) {
        wishlistRemover.removeWishlist(userId, gatheringId);
    }

    public List<GatheringPreview> getMyWishlistGatherings(Long userId, PaginationInformation paginationInformation) {
        return wishlistReader.getMyWishlistGatherings(userId, paginationInformation);
    }
}
