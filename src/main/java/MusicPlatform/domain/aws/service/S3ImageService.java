package MusicPlatform.domain.aws.service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@Transactional
public class S3ImageService extends S3Service {
    private final SqsService sqsService;

    public S3ImageService(S3Client amazonS3Client, SqsService sqsService) {
        super(amazonS3Client);
        this.sqsService = sqsService;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String url = super.uploadFile(file);
        sqsService.sqsSender(url);
        return url;
    }

    @Override
    protected String getFolder() {
        return "images";
    }
}
