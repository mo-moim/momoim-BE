package com.triplem.momoim.core.domain.wishlist.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Wishlist {
    private Long id;
    private Long userId;
    private Long gatheringId;

    @Builder
    public Wishlist(Long id, Long userId, Long gatheringId) {
        this.id = id;
        this.userId = userId;
        this.gatheringId = gatheringId;
    }

    public static Wishlist create(Long userId, Long gatheringId) {
        return Wishlist.builder()
            .userId(userId)
            .gatheringId(gatheringId)
            .build();
    }
}
