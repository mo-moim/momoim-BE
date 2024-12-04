package com.triplem.momoim.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserActiveLocation {
    private Long id;
    private Long userId;
    private String activeLocationType;
}
