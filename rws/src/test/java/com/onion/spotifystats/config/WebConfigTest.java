package com.onion.spotifystats.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebConfigTest {

    private WebConfig webConfig;
    private CorsRegistry corsRegistry;
    private CorsRegistration corsRegistration;

    @BeforeEach
    void setUp() {
        webConfig = new WebConfig();
        corsRegistry = mock(CorsRegistry.class);
        corsRegistration = mock(CorsRegistration.class);
        
        when(corsRegistry.addMapping(any(String.class))).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(any(String.class))).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(any(String.class))).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(any(String.class))).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(any(Boolean.class))).thenReturn(corsRegistration);
    }

    @Test
    void testAddCorsMappings() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("http://localhost:5173");
        verify(corsRegistration).allowedMethods("*");
        verify(corsRegistration).allowedHeaders("*");
        verify(corsRegistration).allowCredentials(true);
    }

    @Test
    void testCorsMappingPath() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistry).addMapping("/**");
    }

    @Test
    void testCorsAllowedOrigins() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistration).allowedOrigins("http://localhost:5173");
    }

    @Test
    void testCorsAllowedMethods() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistration).allowedMethods("*");
    }

    @Test
    void testCorsAllowedHeaders() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistration).allowedHeaders("*");
    }

    @Test
    void testCorsAllowCredentials() {
        webConfig.addCorsMappings(corsRegistry);
        
        verify(corsRegistration).allowCredentials(true);
    }
}