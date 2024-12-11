package com.triplem.momoim.core.domain.wishlist.infrastructure;

import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {
    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        return wishlistJpaRepository.save(WishlistEntity.from(wishlist)).toModel();
    }

    @Override
    public Boolean isAlreadyAppended(Long userId, Long gatheringId) {
        return wishlistJpaRepository.existsByUserIdAndGatheringId(userId, gatheringId);
    }
}
