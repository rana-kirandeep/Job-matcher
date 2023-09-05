import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.swipejobs.kirandeep.SwipeJobsApplication;
import com.swipejobs.kirandeep.response.ErrorResponse;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = SwipeJobsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {ComponentTestInitilizer.class})
@ActiveProfiles("test")
class MatchJobsTest {

    @Autowired
    WireMockServer wireMockServer;
    @Autowired
    TestRestTemplate testRestTemplate;
    @Value("${api.workers.uri}")
    private String URI_WORKERS;
    @Value("${api.jobs.uri}")
    private String URI_JOBS;
    @LocalServerPort
    private Integer port;
    @Autowired
    private ResourceLoader resourceLoader;


    //TODO There are more edge case that can be covered below are just few Just to demonstrate Component test

    @Test
    public void whenReqWorkerWithIdThenReturnMatchingJob() throws JSONException, IOException {

        String mockWorkerResponseFile = "mock_response/worker/workers.json";
        mockThirdPartyApiResponse(URI_WORKERS, mockWorkerResponseFile);

        String mockJobsResponseFile = "mock_response/job/jobs.json";
        mockThirdPartyApiResponse(URI_JOBS, mockJobsResponseFile);


        String localUrl = "http://localhost:" + port + "/api/workers/10";

        ResponseEntity<String> actualJsonResponse = this.testRestTemplate.getForEntity(localUrl, String.class);

        String expectedResponseFilePath = "classpath:__files/expected_response/matchingJobs.json";

        Resource resource = resourceLoader.getResource(expectedResponseFilePath);
        String expectedJSONString = new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);

        JSONAssert.assertEquals(expectedJSONString, actualJsonResponse.getBody().toString(), true);


    }

    @Test
    public void whenReqWorkerWithInvalidIdThenReturnMerchantNotFoundError() throws JSONException, IOException {

        String mockWorkerResponseFile = "mock_response/worker/workers.json";
        mockThirdPartyApiResponse(URI_WORKERS, mockWorkerResponseFile);

        String mockJobsResponseFile = "mock_response/job/jobs.json";
        mockThirdPartyApiResponse(URI_JOBS, mockJobsResponseFile);

        String workerId = "1986";
        String localUrl = "http://localhost:" + port + "/api/workers/" + workerId;

        ResponseEntity<ErrorResponse> responseEntity = this.testRestTemplate.getForEntity(localUrl, ErrorResponse.class);

        ErrorResponse errorResponse = responseEntity.getBody();

        assertThat(errorResponse.code(), is("1001"));
        assertThat(errorResponse.errorMessage(), is("Worker not found."));
        assertThat(errorResponse.details(), is("Worker not found with ID:" + workerId));

    }


    @Test
    public void whenReqWorkerWithValidIdAndWorkerApiReturnNothingThenReturnMerchantNotFoundError() throws JSONException, IOException {

        String mockWorkerResponseFile = "mock_response/worker/empty_workers.json";
        mockThirdPartyApiResponse(URI_WORKERS, mockWorkerResponseFile);

        String mockJobsResponseFile = "mock_response/job/jobs.json";
        mockThirdPartyApiResponse(URI_JOBS, mockJobsResponseFile);

        String workerId = "1986";
        String localUrl = "http://localhost:" + port + "/api/workers/" + workerId;

        ResponseEntity<ErrorResponse> responseEntity = this.testRestTemplate.getForEntity(localUrl, ErrorResponse.class);

        ErrorResponse errorResponse = responseEntity.getBody();

        assertThat(errorResponse.code(), is("1001"));
        assertThat(errorResponse.errorMessage(), is("Worker not found."));
        assertThat(errorResponse.details(), is("Worker not found with ID:" + workerId));

    }


    private void mockThirdPartyApiResponse(String path, String mockWorkerResponseFile) {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo(path))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile(mockWorkerResponseFile)));
    }


}