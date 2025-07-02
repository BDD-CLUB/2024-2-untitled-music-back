package MusicPlatform.global.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client amazonS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(region))
                .build();
    }
}
