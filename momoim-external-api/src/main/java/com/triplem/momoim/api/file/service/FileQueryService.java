package com.triplem.momoim.api.file.service;

import com.triplem.momoim.api.file.response.PreSignedUrlResponse;
import com.triplem.momoim.aws.s3.S3FileService;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileQueryService {
    private final S3FileService s3FileService;

    public PreSignedUrlResponse getPreSignedUrl(Long userId, String prefix, String fileName) {
        if (userId == -1) {
            throw new BusinessException(ExceptionCode.ACCESS_DENIED);
        }
        String preSignedUrl = s3FileService.getPreSignedUrl(prefix, fileName);
        return PreSignedUrlResponse.from(preSignedUrl);
    }
}
