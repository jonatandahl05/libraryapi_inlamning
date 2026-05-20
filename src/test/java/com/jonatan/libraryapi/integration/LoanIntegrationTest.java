package com.jonatan.libraryapi.integration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldReturnBadRequestWhenLoaningSameBookTwice() {
        String baseUrl = "http://localhost:" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "admin:password";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        String authorJson = "{\n" +
                "  \"name\": \"J.K. Rowling\"\n" +
                "}";

        ResponseEntity<Map> authorResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/authors",
                new HttpEntity<>(authorJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, authorResponse.getStatusCode());

        Integer authorId = (Integer) authorResponse.getBody().get("id");

        String uniqueIsbn = "978" + String.format("%010d", Math.abs(System.nanoTime() % 10_000_000_000L));

        String bookJson = "{\n" +
                "  \"title\": \"Harry Potter\",\n" +
                "  \"authorId\": " + authorId + ",\n" +
                "  \"isbn\": \"" + uniqueIsbn + "\",\n" +
                "  \"publicationYear\": 1997\n" +
                "}";

        ResponseEntity<Map> bookResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/books",
                new HttpEntity<>(bookJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, bookResponse.getStatusCode());

        Integer bookId = (Integer) bookResponse.getBody().get("id");

        String loanJson = "{\n" +
                "  \"bookId\": " + bookId + "\n" +
                "}";

        ResponseEntity<Map> firstLoanResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/loans",
                new HttpEntity<>(loanJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, firstLoanResponse.getStatusCode());

        HttpClientErrorException exception = assertThrows(
        HttpClientErrorException.class,
        () -> restTemplate.postForEntity(
                baseUrl + "/api/v1/loans",
                new HttpEntity<>(loanJson, headers),
                Map.class
        ));

         assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }


    @Test
    void shouldReturnNotFoundWhenBookDoesNotExist() {
        String baseUrl = "http://localhost:" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "admin:password";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        String loanJson = "{\n" +
                "  \"bookId\": 9999\n" +
                "}";

        HttpClientErrorException exception = assertThrows(
        HttpClientErrorException.class,
        () -> restTemplate.postForEntity(
                baseUrl + "/api/v1/loans",
                new HttpEntity<>(loanJson, headers),
                Map.class
        ));

         assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}