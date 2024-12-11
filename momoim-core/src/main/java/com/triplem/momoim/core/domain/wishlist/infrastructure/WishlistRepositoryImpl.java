package com.triplem.momoim.core.domain.wishlist.infrastructure;

import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
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

    @Override
    public Wishlist findByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return wishlistJpaRepository.findByUserIdAndGatheringId(userId, gatheringId)
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_WISHLIST))
            .toModel();
    }

    @Override
    public void deleteById(Long id) {
        wishlistJpaRepository.deleteById(id);
    }
}
