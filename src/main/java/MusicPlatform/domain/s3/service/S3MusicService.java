package MusicPlatform.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;

@Service
public class S3MusicService extends S3Service{
    public S3MusicService(AmazonS3 amazonS3Client) {
        super(amazonS3Client);
    }

    @Override
    protected String getFolder() {
        return "musics";
    }
}
