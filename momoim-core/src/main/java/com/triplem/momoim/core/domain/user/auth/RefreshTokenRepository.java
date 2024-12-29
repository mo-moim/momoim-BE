package com.triplem.momoim.core.domain.user.auth;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    void delete(RefreshToken refreshToken);

    RefreshToken findRefreshTokenByUserIdAndToken(Long userId, String token);
}
