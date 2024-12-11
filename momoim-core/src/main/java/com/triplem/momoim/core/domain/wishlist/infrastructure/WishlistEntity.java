package com.triplem.momoim.core.domain.wishlist.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wishlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long gatheringId;

    @Builder
    public WishlistEntity(Long id, Long userId, Long gatheringId) {
        this.id = id;
        this.userId = userId;
        this.gatheringId = gatheringId;
    }
}
