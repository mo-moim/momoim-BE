package com.triplem.momoim.core.domain.user.auth;

import com.triplem.momoim.core.domain.user.User;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
