package com.onion.spotifystats.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testErrorResponseCreation() {
        ErrorResponse response = new ErrorResponse("Test message");
        
        assertNotNull(response);
        assertEquals("Test message", response.Message());
    }

    @Test
    void testErrorResponseWithNullMessage() {
        ErrorResponse response = new ErrorResponse(null);
        
        assertNotNull(response);
        assertNull(response.Message());
    }

    @Test
    void testErrorResponseWithEmptyMessage() {
        ErrorResponse response = new ErrorResponse("");
        
        assertNotNull(response);
        assertEquals("", response.Message());
    }

    @Test
    void testErrorResponseEquality() {
        ErrorResponse response1 = new ErrorResponse("Same message");
        ErrorResponse response2 = new ErrorResponse("Same message");
        
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testErrorResponseInequality() {
        ErrorResponse response1 = new ErrorResponse("Message 1");
        ErrorResponse response2 = new ErrorResponse("Message 2");
        
        assertNotEquals(response1, response2);
    }

    @Test
    void testErrorResponseToString() {
        ErrorResponse response = new ErrorResponse("Test message");
        
        String toString = response.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("Test message"));
    }
}