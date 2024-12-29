package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.request.SignupRequest;
import com.triplem.momoim.api.auth.request.UserProfileUpdateRequest;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.SignupResponse;
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

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthCommandService {
    private final UserRepository userRepository;
    private final UserInterestCategoryRegister userInterestCategoryRegister;
    private final UserActiveLocationRegister userActiveLocationRegister;
    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final UserActiveLocationRepository userActiveLocationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final TokenCommandService tokenCommandService;

    public SignupResponse signup(SignupRequest request) {
        userRepository.checkDuplicatedUserEmail(request.email());

        String password = passwordEncoder.encode(request.password());
        User user = request.toUser(password);
        User savedUser = userRepository.save(user);

        userInterestCategoryRegister.register(savedUser.getId(), request.interestCategories());
        userActiveLocationRegister.register(savedUser.getId(), request.regions());
        return SignupResponse.from(savedUser);
    }

    public UserDetailResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        if (userId == -1) {
            throw new BusinessException(ExceptionCode.ACCESS_DENIED);
        }
        User user = userRepository.findById(userId);
        User updatedUser = request.toUpdatedUser(user);
        User savedUser = userRepository.save(updatedUser);

        userActiveLocationRegister.modify(savedUser.getId(), request.regions());
        userInterestCategoryRegister.modify(savedUser.getId(), request.interestCategories());

        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(savedUser.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(savedUser.getId());

        return UserDetailResponse.from(savedUser, userActiveLocations, userInterestCategories);
    }

    public SigninResponse socialLogin(
            String email,
            String name,
            String profileImageUrl,
            AccountType accountType,
            HttpServletResponse response)
    {
        checkDuplicatedEmail(email, accountType);

        if (userRepository.existsByEmailAndGoogleAccountType(email)) {
            return getSigninResponseFromUser(email, response);
        }

        if (userRepository.existsByEmailAndKakaoAccountType(email)) {
            return getSigninResponseFromUser(email, response);
        }

        User user = User.builder()
                .email(email)
                .name(name)
                .accountType(accountType)
                .profileImage(profileImageUrl)
                .createdAt(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(user);
        userInterestCategoryRegister.register(savedUser.getId(), List.of("ALL"));
        userActiveLocationRegister.register(savedUser.getId(), List.of("ALL"));

        TokenInfo tokenInfo = jwtProvider.generateAccessToken(savedUser);
        tokenCommandService.storeAccessTokenInCookie(tokenInfo, response);
        tokenCommandService.storeRefreshTokenInCookie(savedUser.getId(), tokenInfo, response);

        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(savedUser.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(savedUser.getId());

        return SigninResponse.from(user, tokenInfo, userActiveLocations, userInterestCategories);
    }

    private void checkDuplicatedEmail(String email, AccountType accountType) {
        userRepository.checkDuplicatedUserEmailAndEmailAccountType(email);

        if (userRepository.existsByEmailAndGoogleAccountType(email) && accountType.equals(AccountType.KAKAO)) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_HAS_DUPLICATED_EMAIL);
        }

        if (userRepository.existsByEmailAndKakaoAccountType(email) && accountType.equals(AccountType.GOOGLE)) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_HAS_DUPLICATED_EMAIL);
        }
    }

    private SigninResponse getSigninResponseFromUser(String email, HttpServletResponse response) {
        User findedUser = userRepository.findUserByEmail(email);

        TokenInfo tokenInfo = jwtProvider.generateAccessToken(findedUser);
        tokenCommandService.storeAccessTokenInCookie(tokenInfo, response);
        tokenCommandService.storeRefreshTokenInCookie(findedUser.getId(), tokenInfo, response);

        List<UserActiveLocation> userActiveLocations = userActiveLocationRepository.findAllByUserId(findedUser.getId());
        List<UserInterestCategory> userInterestCategories = userInterestCategoryRepository.findAllByUserId(findedUser.getId());

        return SigninResponse.from(findedUser, tokenInfo, userActiveLocations, userInterestCategories);
    }
}
