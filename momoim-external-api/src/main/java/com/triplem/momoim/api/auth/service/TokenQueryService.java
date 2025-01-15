package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.core.domain.user.auth.RefreshToken;
import com.triplem.momoim.core.domain.user.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenQueryService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken getByUserIdAndToken(Long userId, String token) {
        return refreshTokenRepository.findRefreshTokenByUserIdAndToken(userId, token);
    }
}
