package MusicPlatform.domain.aws.service;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public abstract class S3Service {

    private final S3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String key = getFileKey(file.getOriginalFilename());
            amazonS3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentLength(file.getSize())
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
            return "s3://" + bucketName + "/" + key;
        } catch (SdkException e) {
            throw new IOException("S3 업로드 실패: " + e.getMessage(), e);
        }
    }

    public String getFileKey(String originalFilename) {
        return "resources/" + getFolder() + "/" + UUID.randomUUID() + "_" + originalFilename;
    }

    protected abstract String getFolder();
}
