package MusicPlatform.domain.aws.service;

import static MusicPlatform.global.error.ApplicationError.LAMBDA_NOT_VALID;

import MusicPlatform.domain.aws.service.dto.request.LambdaRequestDto;
import MusicPlatform.domain.aws.service.dto.response.LambdaResponseDto;
import MusicPlatform.global.error.ApplicationException;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.amazonaws.services.lambda.AWSLambda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LambdaService {
    private static final String LAMBDA_FUNCTION_NAME = "ImageCompress2";

    private final AWSLambda awsLambda;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public LambdaResponseDto uploadImage(MultipartFile file) throws IOException {
        validateLambda();
        LambdaRequestDto requestDto = new LambdaRequestDto(
                UUID.randomUUID() + "_" + file.getOriginalFilename(),
                Base64.getEncoder().encodeToString(file.getBytes()),
                bucketName
        );
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(LAMBDA_FUNCTION_NAME)
                .withPayload(objectMapper.writeValueAsString(requestDto));

        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        String lambdaResult = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        return objectMapper.readValue(lambdaResult, LambdaResponseDto.class);
    }

    public void validateLambda() {
        GetFunctionRequest request = new GetFunctionRequest().withFunctionName("ImageCompress2");
        try {
            GetFunctionResult result = awsLambda.getFunction(request);
            log.info("Lambda 접근: " + result.getConfiguration().getFunctionName());
        } catch (Exception e) {
            log.info("Lambda 접근 실패: " + e.getMessage());
            throw new ApplicationException(LAMBDA_NOT_VALID);
        }
    }
}
