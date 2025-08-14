package MusicPlatform.domain.aws.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class SqsService {

    @Value("${cloud.aws.sqs.queue.name}")
    private String queueName;

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    public void sqsSender(String s3Url) {
        String queueUrl = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build()).queueUrl();
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(objectMapper.createObjectNode()
                        .put("s3url", s3Url)
                        .toString())
                .build();

        SendMessageResponse response = sqsClient.sendMessage(request);

        System.out.println(response.md5OfMessageBody());
    }
}
