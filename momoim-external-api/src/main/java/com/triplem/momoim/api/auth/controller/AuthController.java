package com.triplem.momoim.api.auth.controller;

import com.triplem.momoim.api.auth.request.SignupRequest;
import com.triplem.momoim.api.auth.response.SignupResponse;
import com.triplem.momoim.api.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
