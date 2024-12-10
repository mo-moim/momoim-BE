package com.triplem.momoim.api.file.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.file.response.PreSignedUrlResponse;
import com.triplem.momoim.api.file.service.FileQueryService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="File", description = "파일 처리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class S3FileController {
    private final FileQueryService fileQueryService;

    @Operation(summary = "S3 파일 업로드 presigned URL 생성 API",
            description = "S3 파일 업로드 presigned URL을 생성하는 API")
    @GetMapping("/presigned-url/{prefix}/{fileName}")
    public ApiResponse<PreSignedUrlResponse> getPreSignedUrl(
            @PathVariable("prefix") String prefix,
            @PathVariable("fileName") String fileName
    ) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        PreSignedUrlResponse response = fileQueryService.getPreSignedUrl(userId, prefix, fileName);
        return ApiResponse.success(response);
    }
}
