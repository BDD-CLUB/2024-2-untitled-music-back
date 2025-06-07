package MusicPlatform.domain.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public abstract class S3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        ObjectMetadata metadata = getObjectMetadata(file);
        String path = getFilePath(file.getOriginalFilename());
        amazonS3Client.putObject(new PutObjectRequest(bucketName, path, file.getInputStream(), metadata));
        return amazonS3Client.getUrl(bucketName, path).toString();
    }

    protected ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }

    public String getFilePath(String originalFilename) {
        return "resources/" + getFolder() + "/" + UUID.randomUUID() + "_" + originalFilename;
    }

    protected abstract String getFolder();
}
