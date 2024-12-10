package com.triplem.momoim.aws.s3;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private static final int EXPIRATION_TIME_MINUTES = 1;
    private final S3Template s3Template;

    /**
     * Get presigned URL for S3 file upload
     * @param prefix s3 layer
     * @param fileName file name
     * @return presigned URL (PUT method)
     */
    public String getPreSignedUrl(String prefix, String fileName) {
        String generatedFileName = generateFileName(prefix, fileName);
        URL presignedUrl = s3Template.createSignedPutURL(bucket, generatedFileName, generateExpiration());
        return presignedUrl.toString();
    }

    /**
     * Delete file from S3 bucket
     * @param fileName file name
     */
    public void deleteFile(String fileName) {
        if (s3Template.objectExists(bucket, fileName)) {
            s3Template.deleteObject(bucket, fileName);
        }
    }

    /**
     * Generate file name with prefix and origin file name
     * @param prefix s3 layer
     * @param fileName file name
     * @return generated file name
     * @Example profile/2b6f0b4f-1b7d-...-0b1f1b0f1b0f-profile1.jpg
     */
    public String generateFileName(String prefix, String fileName) {
        final String uuid = UUID.randomUUID().toString();
        return prefix + "/" + uuid + "-" + fileName;
    }

    /**
     * Generate expiration time for presigned URL
     * @return expiration time
     */
    private Duration generateExpiration() {
        return Duration.ofMinutes(EXPIRATION_TIME_MINUTES);
    }
}
