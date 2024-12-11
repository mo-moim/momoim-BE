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
}
