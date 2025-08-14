package MusicPlatform.domain.aws.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3MusicService extends S3Service{
    public S3MusicService(S3Client amazonS3Client) {
        super(amazonS3Client);
    }

    @Override
    protected String getFolder() {
        return "musics";
    }
}
