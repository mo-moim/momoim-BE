package com.triplem.momoim.core.domain.wishlist.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistJpaRepository extends JpaRepository<WishlistEntity, Long> {
    Boolean existsByUserIdAndGatheringId(Long userId, Long gatheringId);

    Optional<WishlistEntity> findByUserIdAndGatheringId(Long userId, Long gatheringId);
}
