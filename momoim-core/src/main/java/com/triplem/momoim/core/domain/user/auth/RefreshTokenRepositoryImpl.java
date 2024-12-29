package com.triplem.momoim.core.domain.user.auth;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(RefreshTokenEntity.from(refreshToken)).toModel();
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenJpaRepository.delete(RefreshTokenEntity.from(refreshToken));
    }

    @Override
    public RefreshToken findRefreshTokenByUserIdAndToken(Long userId, String token) {
        return refreshTokenJpaRepository.findRefreshTokenByUserIdAndToken(userId, token).orElseThrow(
                () -> new BusinessException(ExceptionCode.NOT_FOUND_REFRESH_TOKEN)
        ).toModel();
    }
}
