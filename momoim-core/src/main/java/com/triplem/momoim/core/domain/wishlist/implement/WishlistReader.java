package com.triplem.momoim.core.domain.wishlist.implement;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.wishlist.infrastructure.WishlistRepository;
import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistReader {
    private final WishlistRepository wishlistRepository;
    private final GatheringRepository gatheringRepository;

    public List<GatheringPreview> getMyWishlistGatherings(Long userId, PaginationInformation paginationInformation) {
        List<Wishlist> wishlist = wishlistRepository.getMyWishlist(userId, paginationInformation);
        List<Long> gatheringIds = wishlist.stream().map(Wishlist::getGatheringId).toList();
        return gatheringRepository.getGatheringPreviewsById(userId, gatheringIds)
            .stream()
            .sorted(Comparator.comparingInt(gathering -> gatheringIds.indexOf(gathering.getGatheringId())))
            .collect(Collectors.toList());
    }
}
