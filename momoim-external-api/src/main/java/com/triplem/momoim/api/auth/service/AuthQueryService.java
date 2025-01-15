package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.request.SigninRequest;
import com.triplem.momoim.api.auth.response.CheckEmailNicknameResponse;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.UserDetailResponse;
import com.triplem.momoim.auth.jwt.JwtProvider;
import com.triplem.momoim.auth.jwt.TokenInfo;
import com.triplem.momoim.core.domain.user.*;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private static final String DUPLICATED_CHECK_ALLOW = "ALLOW";

    private final UserRepository userRepository;
    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final UserActiveLocationRepository userActiveLocationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final TokenCommandService tokenCommandService;

    public SigninResponse signin(SigninRequest request, HttpServletResponse response) {
        User user = userRepository.findUserByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ExceptionCode.INVALID_LOGIN);
        }
        TokenInfo accessTokenInfo = jwtProvider.generateAccessToken(user);
        TokenInfo refreshTokenInfo = jwtProvider.generateRefreshToken(user);

        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(user.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(user.getId());

        // set cookie
//        tokenCommandService.storeAccessTokenInCookie(accessTokenInfo, response);
        tokenCommandService.storeRefreshTokenInCookie(user.getId(), refreshTokenInfo, response);

        return SigninResponse.from(user, accessTokenInfo, userActiveLocations, userInterestCategories);
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

    public CheckEmailNicknameResponse checkDuplicatedEmail(String email) {
        userRepository.checkDuplicatedUserEmail(email);
        return new CheckEmailNicknameResponse(DUPLICATED_CHECK_ALLOW);
    }

    public CheckEmailNicknameResponse checkDuplicatedNickname(String name) {
        userRepository.checkDuplicatedUserName(name);
        return new CheckEmailNicknameResponse(DUPLICATED_CHECK_ALLOW);
    }
}
