package com.onion.spotifystats.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SpotifyApiClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    }

    @Test
    void testSpotifyApiClientCreation() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);

        assertNotNull(client);
    }

    @Test
    void testApiUrlConstant() {
        assertEquals("https://api.spotify.com/v1", SpotifyApiClient.API_URL);
    }

    @Test
    void throwsWhenNoAuthenticationIsPresent() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> { Consumer<HttpHeaders> consumer = invocation.getArgument(0); HttpHeaders headers = new HttpHeaders(); consumer.accept(headers); return requestBodySpec; });

        assertThrows(IllegalStateException.class, () -> client.get("/me", String.class));
    }

    @Test
    void throwsWhenAuthorizedClientManagerReturnsNull() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> { Consumer<HttpHeaders> consumer = invocation.getArgument(0); HttpHeaders headers = new HttpHeaders(); consumer.accept(headers); return requestBodySpec; });
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> client.get("/me", String.class));
    }

    @Test
    void throwsWhenAccessTokenIsMissing() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AuthorizedClient authorizedClient = authorizedClient(null);
        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> { Consumer<HttpHeaders> consumer = invocation.getArgument(0); HttpHeaders headers = new HttpHeaders(); consumer.accept(headers); return requestBodySpec; });
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(authorizedClient);

        assertThrows(IllegalStateException.class, () -> client.get("/me", String.class));
    }

    @Test
    void throwsWhenAccessTokenValueIsNull() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn(null);
        OAuth2AuthorizedClient oauthClient = authorizedClient(accessToken);
        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> { Consumer<HttpHeaders> consumer = invocation.getArgument(0); HttpHeaders headers = new HttpHeaders(); consumer.accept(headers); return requestBodySpec; });
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(oauthClient);

        assertThrows(IllegalStateException.class, () -> client.get("/me", String.class));
    }

    @Test
    void usesBearerTokenForGetRequests() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn("spotify-token");
        OAuth2AuthorizedClient authorizedClient = authorizedClient(accessToken);
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(authorizedClient);

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> {
            Consumer<HttpHeaders> consumer = invocation.getArgument(0);
            HttpHeaders headers = new HttpHeaders();
            consumer.accept(headers);
            assertEquals("Bearer spotify-token", headers.getFirst(HttpHeaders.AUTHORIZATION));
            return requestBodySpec;
        });
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("ok"));

        String response = client.get("/me", String.class);

        assertEquals("ok", response);
    }

    @Test
    void usesBearerTokenForFunctionUriRequests() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn("spotify-token");
        OAuth2AuthorizedClient authorizedClient = authorizedClient(accessToken);
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(authorizedClient);

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> {
            Consumer<HttpHeaders> consumer = invocation.getArgument(0);
            HttpHeaders headers = new HttpHeaders();
            consumer.accept(headers);
            assertEquals("Bearer spotify-token", headers.getFirst(HttpHeaders.AUTHORIZATION));
            return requestBodySpec;
        });
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("ok"));

        String response = client.get(uriBuilder -> uriBuilder.path("/me").build(), String.class);

        assertEquals("ok", response);
    }

    @Test
    void sendsBodiesForPutRequestsAndReturnsVoid() {
        SpotifyApiClient client = new SpotifyApiClient(webClient, authorizedClientManager);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn("spotify-token");
        OAuth2AuthorizedClient authorizedClient = authorizedClient(accessToken);
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(authorizedClient);

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenAnswer(invocation -> { Consumer<HttpHeaders> consumer = invocation.getArgument(0); HttpHeaders headers = new HttpHeaders(); consumer.accept(headers); return requestBodySpec; });
        lenient().when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        assertNull(client.put("/me", "payload", Void.class));
    }

    private OAuth2AuthenticationToken authenticationToken() {
        OAuth2AuthenticationToken token = mock(OAuth2AuthenticationToken.class);
        lenient().when(token.getAuthorizedClientRegistrationId()).thenReturn("spotify");
        lenient().when(token.getName()).thenReturn("user");
        return token;
    }

    private OAuth2AuthorizedClient authorizedClient(OAuth2AccessToken accessToken) {
        OAuth2AuthorizedClient client = mock(OAuth2AuthorizedClient.class);
        lenient().when(client.getAccessToken()).thenReturn(accessToken);
        return client;
    }
}