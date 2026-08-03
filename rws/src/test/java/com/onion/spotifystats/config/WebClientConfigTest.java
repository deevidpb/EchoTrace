package com.onion.spotifystats.config;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebClientConfigTest {

    private final WebClientConfig config = new WebClientConfig();

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "https://api.spotify.com/v1",
            "https://custom.api.com/v1",
            "http://localhost:8080"
    })
    void testWebClientCreation(String baseUrl) {
        WebClient webClient = config.webClient(baseUrl);

        assertNotNull(webClient);
    }
}