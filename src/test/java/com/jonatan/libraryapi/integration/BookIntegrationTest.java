package com.jonatan.libraryapi.integration;

import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldReturnBooksInV2Format() {
        String baseUrl = "http://localhost:" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = "admin:password";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);

        String authorJson = "{\n" +
                "  \"name\": \"George Orwell\"\n" +
                "}";

        ResponseEntity<Map> authorResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/authors",
                new HttpEntity<>(authorJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, authorResponse.getStatusCode());

        Integer authorId = (Integer) authorResponse.getBody().get("id");

        String uniqueIsbn = "isbn-" + System.nanoTime();

        String bookJson = String.format(
            "{\n" +
            " \"title\": \"1984\",\n" +
            " \"authorId\": %d,\n" +
            " \"isbn\": \"%s\",\n" +
            " \"publicationYear\": 1949\n" +
            "}",
            authorId, uniqueIsbn
        );

        ResponseEntity<Map> bookResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/books",
                new HttpEntity<>(bookJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, bookResponse.getStatusCode());

        ResponseEntity<Map> v2Response = restTemplate.exchange(
                baseUrl + "/api/v2/books",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        assertEquals(HttpStatus.OK, v2Response.getStatusCode());
        assertEquals("v2", v2Response.getBody().get("version"));
        assertNotNull(v2Response.getBody().get("data"));
    }
}