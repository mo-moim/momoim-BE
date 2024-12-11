package com.triplem.momoim.core.domain.wishlist.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistJpaRepository extends JpaRepository<WishlistEntity, Long> {
}
