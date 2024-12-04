package com.triplem.momoim.api.auth.service;

import com.triplem.momoim.api.auth.request.SigninRequest;
import com.triplem.momoim.api.auth.request.SignupRequest;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.SignupResponse;
import com.triplem.momoim.core.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthCommandService {
    private final UserRepository userRepository;
    private final UserInterestCategoryRegister userInterestCategoryRegister;
    private final UserActiveLocationRegister userActiveLocationRegister;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {
        userRepository.checkDuplicatedUserEmail(request.email());

        String password = passwordEncoder.encode(request.password());
        User user = request.toUser(password);
        User savedUser = userRepository.save(user);

        userInterestCategoryRegister.register(savedUser.getId(), request.interestCategories());
        userActiveLocationRegister.register(savedUser.getId(), request.regions());
        return SignupResponse.from(savedUser);
    }
}
