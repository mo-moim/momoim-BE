package com.triplem.momoim.core.domain.user.auth;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class RefreshToken {
    private Long id;
    private Long userId;
    private String token;
    private Date expiredAt;
}
