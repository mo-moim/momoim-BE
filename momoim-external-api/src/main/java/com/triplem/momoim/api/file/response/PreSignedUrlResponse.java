package com.triplem.momoim.api.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "PreSigned URL 반환 응답")
public record PreSignedUrlResponse(
        @Schema(description = "PreSigned URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/.../...")
        String preSignedUrl
) {
    public static PreSignedUrlResponse from(String preSignedUrl) {
        return PreSignedUrlResponse.builder()
                .preSignedUrl(preSignedUrl)
                .build();
    }
}
