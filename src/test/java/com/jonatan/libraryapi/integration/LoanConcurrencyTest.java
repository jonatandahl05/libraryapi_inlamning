package com.jonatan.libraryapi.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanConcurrencyTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldOnlyCreateOneLoanWhenManyRequestsTryToLoanSameBook() throws Exception {
        String baseUrl = "http://localhost:" + port;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authorJson = """
                {
                  "name": "Concurrent Author"
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
          "title": "Concurrent Book",
          "authorId": %d,
          "isbn": "%s",
          "publicationYear": 2024
        }
        """.formatted(authorId, uniqueIsbn);

        ResponseEntity<Map> bookResponse = restTemplate.postForEntity(
                baseUrl + "/api/v1/books",
                new HttpEntity<>(bookJson, headers),
                Map.class
        );

        assertEquals(HttpStatus.CREATED, bookResponse.getStatusCode());
        Integer bookId = (Integer) bookResponse.getBody().get("id");

        String loanJson = """
                {
                  "bookId": %d
                }
                """.formatted(bookId);

        int numberOfRequests = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < numberOfRequests; i++) {
            tasks.add(() -> {
                try {
                    ResponseEntity<Map> response = restTemplate.postForEntity(
                            baseUrl + "/api/v1/loans",
                            new HttpEntity<>(loanJson, headers),
                            Map.class
                    );
                    return response.getStatusCode().value();

                } catch (HttpClientErrorException ex) {
                    return ex.getStatusCode().value();

                } catch (HttpServerErrorException ex) {
                    return ex.getStatusCode().value();
                }
            });
        }

        List<Future<Integer>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();

        int createdCount = 0;
        int rejectedCount = 0;

        for (Future<Integer> future : futures) {
            int status = future.get();

            if (status == 201) {
                createdCount++;
            } else if (status == 400 || status == 409) {
                rejectedCount++;
            }
        }

        assertTrue(createdCount >= 1);
        assertTrue(rejectedCount >= 1);

        ResponseEntity<List> allLoansResponse = restTemplate.getForEntity(
        baseUrl + "/api/v1/loans",
        List.class
        );

        assertEquals(HttpStatus.OK, allLoansResponse.getStatusCode());

        long loansForThisBook = allLoansResponse.getBody().stream()
        .filter(item -> {
            Map loanMap = (Map) item;
            Object returnedBookId = loanMap.get("bookId");
            return returnedBookId != null
                    && ((Number) returnedBookId).longValue() == bookId.longValue();
        })
        .count();

        assertEquals(1, loansForThisBook);
    }
}