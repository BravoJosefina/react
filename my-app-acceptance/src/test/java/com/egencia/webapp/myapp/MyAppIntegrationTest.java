package com.egencia.webapp.myapp;

import com.egencia.commons.mvc.client.builder.RestTemplateBuilder;
import com.egencia.webapp.myapp.WiremockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class, WiremockConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class MyAppIntegrationTest {

    private static final String ROOT_PATH = "/my-app";

    @LocalServerPort
    private String appPort;

    private RestTemplate restTemplate;
    private UriComponentsBuilder uriBuilder;


    @BeforeEach
    public void setup() {
        this.restTemplate = new RestTemplateBuilder().build();
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost")
            .port(appPort)
            .path(ROOT_PATH);
    }

    /**
     * A basic test that hits the /base/version endpoint of your app
     * and verifies a few attributes of the response
     */
    @Test
    public void testBaseVersion() {
        final URI uri = uriBuilder.path("/base/version").build().toUri();
        final ResponseEntity<Map> versionResponse = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(null), Map.class);
        assertTrue(versionResponse.hasBody());

        Map baseVersion = versionResponse.getBody();
        assertNotNull(baseVersion);

        assertTrue(baseVersion.containsKey("application_name"));
        assertEquals(baseVersion.get("application_name"), "my-app");

        assertTrue(baseVersion.containsKey("build_version"));
        assertNotEquals(0, ((String) baseVersion.get("build_version")).length());
    }

    /**
     * A basic test that hits the root path of your app
     *  and verifies that the response has a body
     */
    @Test
    public void testRootPath() {
        final URI uri = uriBuilder.path("/").build().toUri();
        final ResponseEntity<String> versionResponse =  restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(null), String.class);
        assertTrue(versionResponse.hasBody());
    }
}
