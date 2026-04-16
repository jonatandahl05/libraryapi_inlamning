package com.jonatan.libraryapi.integration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

        String authorJson = """
                {
                  "name": "George Orwell"
                }
                """;

        ResponseEntity<Map> authorResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/authors",
                new HttpEntity<>(authorJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, authorResponse.getStatusCode());

        Integer authorId = (Integer) authorResponse.getBody().get("id");

        String uniqueIsbn = "isbn-" + System.nanoTime();

        String bookJson = """
            {
             "title": "1984",
             "authorId": %d,
             "isbn": "%s",
             "publicationYear": 1949
            }
        """.formatted(authorId, uniqueIsbn);

        ResponseEntity<Map> bookResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/books",
                new HttpEntity<>(bookJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, bookResponse.getStatusCode());

        ResponseEntity<Map> v2Response = restTemplate.getForEntity(
                baseUrl + "/api/v2/books",
                Map.class
        );

        assertEquals(HttpStatus.OK, v2Response.getStatusCode());
        assertEquals("v2", v2Response.getBody().get("version"));
        assertNotNull(v2Response.getBody().get("data"));
    }
}