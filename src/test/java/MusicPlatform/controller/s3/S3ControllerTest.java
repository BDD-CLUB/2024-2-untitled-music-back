package MusicPlatform.controller.s3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import MusicPlatform.helper.ApiTestHelper;
import MusicPlatform.helper.cleanData;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

@cleanData
public class S3ControllerTest extends ApiTestHelper {
    private static final String TEST_IMAGE_URL = "src/test/resources/file_upload_test_image.jpg";

    private MockMultipartFile file;

    @BeforeEach
    public void setUp() throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(TEST_IMAGE_URL));
        String fileName = "file_upload_test_image.jpg";
        file = new MockMultipartFile(
                "file",
                fileName,
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes);
    }

    // ~/.aws 파일로 자격증명 읽음
    @Test
    @Disabled
    @DisplayName("이미지를 업로드 할 수 있다.")
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void 이미지를_업로드_할_수_있다() throws Exception {
        //when
        MvcResult result = mockMvc.perform(multipart("/uploads/images")
                        .file(file))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(jsonResponse);
        // https://soundflyer.s3.ap-northeast-2.amazonaws.com/resources/images/feab0309-f822-46b8-85d7-5e90a376cf36_file_upload_test_image
    }
}
