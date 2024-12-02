package com.triplem.momoim.api.test;

import com.triplem.momoim.auth.jwt.JwtProvider;
import com.triplem.momoim.auth.jwt.TokenInfo;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.domain.user.User;
import com.triplem.momoim.core.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserIdFromJWT() {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/token/{userId}")
    public ResponseEntity<TokenInfo> generateToken(@PathVariable Long userId) {
        User user = userRepository.findById(userId);
        TokenInfo token = jwtProvider.generateAccessToken(user);
        return ResponseEntity.ok(token);
    }
}
