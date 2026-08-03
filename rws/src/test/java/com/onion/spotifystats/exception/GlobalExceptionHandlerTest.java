package com.onion.spotifystats.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIllegalState() {
        IllegalStateException exception = new IllegalStateException("Test illegal state message");
        
        ResponseEntity<ErrorResponse> response = handler.handleIllegalState(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test illegal state message", response.getBody().Message());
    }

    @Test
    void handleIllegalStateWithNullMessage() {
        IllegalStateException exception = new IllegalStateException();
        
        ResponseEntity<ErrorResponse> response = handler.handleIllegalState(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleClient() {
        WebClientResponseException exception = new WebClientResponseException(
            401, "Unauthorized", null, null, null
        );
        
        ResponseEntity<ErrorResponse> response = handler.handleClient(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleClientWithCustomMessage() {
        WebClientResponseException exception = new WebClientResponseException(
            403, "Forbidden", null, "Custom error message".getBytes(), null
        );
        
        ResponseEntity<ErrorResponse> response = handler.handleClient(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().Message().contains("403"));
    }

    @Test
    void handleClientWith500Error() {
        WebClientResponseException exception = new WebClientResponseException(
            500, "Internal Server Error", null, null, null
        );
        
        ResponseEntity<ErrorResponse> response = handler.handleClient(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}