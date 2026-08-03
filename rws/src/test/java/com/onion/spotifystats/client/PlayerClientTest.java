package com.onion.spotifystats.client;

import com.onion.spotifystats.dto.request.player.PlayRequestDTO;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.PlayerState;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    }

    @Test
    void testPlayerClientCreation() {
        PlayerClient client = new PlayerClient(webClient, authorizedClientManager, "localhost");
        assertNotNull(client);
    }

    @Test
    void testPlayerClientExtendsSpotifyApiClient() {
        PlayerClient client = new PlayerClient(webClient, authorizedClientManager, "localhost");
        assertInstanceOf(SpotifyApiClient.class, client);
    }

    @Test
    void currentlyPlayingAndRecentlyPlayedUseGetRequests() {
        PlayerClient client = new PlayerClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");

        PlayerState playerState = new PlayerState();
        RecentlyPlayedResponse recentlyPlayed = new RecentlyPlayedResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(String.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);

            uriFunction.apply(UriComponentsBuilder.newInstance());

            return requestSpec;
        });

        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(PlayerState.class)).thenReturn(Mono.just(playerState));
        lenient().when(responseSpec.bodyToMono(RecentlyPlayedResponse.class)).thenReturn(Mono.just(recentlyPlayed));

        assertNotNull(client.currentlyPlaying());
        assertNotNull(client.recently(5));
    }

    @Test
    void playerActionsUseExpectedHttpMethods() {
        PlayerClient client = new PlayerClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(String.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);

            uriFunction.apply(UriComponentsBuilder.newInstance());

            return requestSpec;
        });

        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        client.play(new PlayRequestDTO("spotify:album:1", java.util.List.of("spotify:track:1"), 1000));
        client.pause();
        client.next();
        client.previous();
        client.volume(50);

        verify(webClient, times(3)).method(HttpMethod.PUT);
        verify(webClient, times(2)).method(HttpMethod.POST);
    }

    private void configureAuthenticatedClient(String tokenValue) {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());

        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn(tokenValue);

        OAuth2AuthorizedClient authorizedClient = authorizedClient(accessToken);
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class)))
                .thenReturn(authorizedClient);
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