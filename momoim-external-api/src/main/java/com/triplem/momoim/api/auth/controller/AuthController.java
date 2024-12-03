package com.triplem.momoim.api.auth.controller;

import com.triplem.momoim.api.auth.request.SigninRequest;
import com.triplem.momoim.api.auth.request.SignupRequest;
import com.triplem.momoim.api.auth.request.UserProfileUpdateRequest;
import com.triplem.momoim.api.auth.response.SigninResponse;
import com.triplem.momoim.api.auth.response.SignupResponse;
import com.triplem.momoim.api.auth.response.UserDetailResponse;
import com.triplem.momoim.api.auth.response.common.Token;
import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.core.domain.user.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/signup")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = SignupResponse.class)))
    })
    @Operation(operationId = "회원가입", summary = "회원가입", tags = {"auths"}, description = "이메일 로그인을 위한 회원가입")
    public ApiResponse<SignupResponse> signup(@RequestBody SignupRequest request) {
        return ApiResponse.success(new SignupResponse("email", "name", "profileImage", "accountType"));
    }

    @PostMapping("/signin")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = SigninResponse.class)))
    })
    @Operation(operationId = "이메일 로그인", summary = "이메일 로그인", tags = {"auths"}, description = "이메일 로그인")
    public ApiResponse<SigninResponse> signin(@RequestBody SigninRequest request) {
        return ApiResponse.success(new SigninResponse(new Token("token", 123456L),"email", "name", "profileImage", List.of("BUSAN", "SEOUL"), List.of("CULTURE, FOOD")));
    }

    @GetMapping("/user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = UserDetailResponse.class)))
    })
    @Operation(operationId = "유저 프로필 조회", summary = "유저 프로필 조회", tags = {"auths"}, description = "유저 프로필 정보 조회")
    public ApiResponse<UserDetailResponse> getUserProfile() {
        return ApiResponse.success(new UserDetailResponse("email", "name", "profileImage", AccountType.EMAIL.name(), List.of("BUSAN", "SEOUL"), List.of("CULTURE, FOOD")));
    }

    @PutMapping("/user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",  content = @Content(schema = @Schema(implementation = UserDetailResponse.class)))
    })
    @Operation(operationId = "유저 프로필 수정", summary = "유저 프로필 수정", tags = {"auths"}, description = "유저 프로필 정보 수정")
    public ApiResponse<UserDetailResponse> updateUserProfile(@RequestBody UserProfileUpdateRequest request) {
        return ApiResponse.success(new UserDetailResponse("email", "name", "profileImage", AccountType.EMAIL.name(), List.of("BUSAN", "SEOUL"), List.of("CULTURE, FOOD")));
    }
}
