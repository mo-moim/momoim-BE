package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.request.SigninRequest;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.UserDetailResponse;
import com.triplem.momoim.auth.jwt.JwtProvider;
import com.triplem.momoim.auth.jwt.TokenInfo;
import com.triplem.momoim.core.domain.user.*;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthQueryService {
    private final UserRepository userRepository;
    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final UserActiveLocationRepository userActiveLocationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SigninResponse signin(SigninRequest request) {
        User user = userRepository.findUserByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ExceptionCode.INVALID_LOGIN);
        }
        TokenInfo tokenInfo = jwtProvider.generateAccessToken(user);
        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(user.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(user.getId());

        return SigninResponse.from(user, tokenInfo, userActiveLocations, userInterestCategories);
    }

    public UserDetailResponse getUserProfile(Long userId) {
        if (userId == -1) {
            throw new BusinessException(ExceptionCode.ACCESS_DENIED);
        }
        User user = userRepository.findById(userId);
        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(user.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(user.getId());

        return UserDetailResponse.from(user, userActiveLocations, userInterestCategories);
    }
}
