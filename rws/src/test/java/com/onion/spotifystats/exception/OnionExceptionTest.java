package com.onion.spotifystats.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OnionExceptionTest {

    @Test
    void testOnionExceptionCreation() {
        OnionException exception = new OnionException("Test error message");
        
        assertNotNull(exception);
        assertEquals("Test error message", exception.getMessage());
    }

    @Test
    void testOnionExceptionWithNullMessage() {
        OnionException exception = new OnionException(null);
        
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testOnionExceptionWithEmptyMessage() {
        OnionException exception = new OnionException("");
        
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void testOnionExceptionCause() {
        Throwable cause = new RuntimeException("Root cause");
        OnionException exception = new OnionException("Wrapped error");
        exception.initCause(cause);
        
        assertNotNull(exception);
        assertEquals("Wrapped error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testOnionExceptionIsException() {
        OnionException exception = new OnionException("Test");
        
        assertTrue(exception instanceof Exception);
    }

    @Test
    void testOnionExceptionStackTrace() {
        OnionException exception = new OnionException("Test");
        
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }
}