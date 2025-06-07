package MusicPlatform.domain.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;

@Service
public class S3ImageService extends S3Service{
    public S3ImageService(AmazonS3 amazonS3Client) {
        super(amazonS3Client);
    }

    @Override
    protected String getFolder() {
        return "images";
    }
}
