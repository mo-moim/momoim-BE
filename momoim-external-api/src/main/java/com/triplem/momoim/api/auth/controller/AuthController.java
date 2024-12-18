package com.triplem.momoim.api.auth.controller;

import com.triplem.momoim.api.auth.request.SigninRequest;
import com.triplem.momoim.api.auth.request.SignupRequest;
import com.triplem.momoim.api.auth.request.UserProfileUpdateRequest;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.SignupResponse;
import com.triplem.momoim.api.auth.response.UserDetailResponse;
import com.triplem.momoim.api.auth.response.common.Token;
import com.triplem.momoim.api.auth.service.AuthCommandService;
import com.triplem.momoim.api.auth.service.AuthQueryService;
import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.domain.user.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AuthCommandService authCommandService;
    private final AuthQueryService authQueryService;

    @PostMapping("/signup")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = SignupResponse.class)))
    })
    @Operation(operationId = "회원가입", summary = "회원가입", tags = {"auths"}, description = "이메일 로그인을 위한 회원가입")
    public ApiResponse<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = authCommandService.signup(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/signin")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = SigninResponse.class)))
    })
    @Operation(operationId = "이메일 로그인", summary = "이메일 로그인", tags = {"auths"}, description = "이메일 로그인")
    public ApiResponse<SigninResponse> signin(@RequestBody SigninRequest request, HttpServletResponse response) {
        return ApiResponse.success(authQueryService.signin(request, response));
    }

    @GetMapping("/user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = UserDetailResponse.class)))
    })
    @Operation(operationId = "유저 프로필 조회", summary = "유저 프로필 조회", tags = {"auths"}, description = "유저 프로필 정보 조회")
    public ApiResponse<UserDetailResponse> getUserProfile() {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        UserDetailResponse response = authQueryService.getUserProfile(userId);
        return ApiResponse.success(response);
    }

    @PutMapping("/user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = UserDetailResponse.class)))
    })
    @Operation(operationId = "유저 프로필 수정", summary = "유저 프로필 수정", tags = {"auths"}, description = "유저 프로필 정보 수정")
    public ApiResponse<UserDetailResponse> updateUserProfile(@RequestBody UserProfileUpdateRequest request) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        UserDetailResponse response = authCommandService.updateUserProfile(userId, request);
        return ApiResponse.success(response);
    }
}
