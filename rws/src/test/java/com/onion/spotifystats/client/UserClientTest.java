package com.onion.spotifystats.client;

import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserFollowedArtistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserPlaylistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserSavedTracksResponse;
import com.onion.spotifystats.model.spotify.response.topartists.TopArtistsResponse;
import com.onion.spotifystats.model.spotify.response.toptracks.TopTracksResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    }

    @Test
    void testUserClientCreation() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        assertNotNull(client);
    }

    @Test
    void testUserClientExtendsSpotifyApiClient() {
        new UserClient(webClient, authorizedClientManager, "localhost");
        assertTrue(true);
    }

    @Test
    void getCurrentUserUsesMeEndpoint() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        CurrentUserResponse response = new CurrentUserResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(anyString())).thenReturn(requestSpec);
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(CurrentUserResponse.class)).thenReturn(Mono.just(response));

        CurrentUserResponse result = client.getCurrentUser();

        assertSame(response, result);
    }

    @Test
    void getTopTracksBuildsExpectedQueryParameters() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        TopTracksResponse response = new TopTracksResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
            uriFunction.apply(UriComponentsBuilder.newInstance());
            return requestSpec;
        });
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(TopTracksResponse.class)).thenReturn(Mono.just(response));

        TopTracksResponse result = client.getTopTracks("short_term", 10, 20);

        assertSame(response, result);
    }

    @Test
    void getTopArtistsUsesArtistsEndpoint() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        TopArtistsResponse response = new TopArtistsResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
            uriFunction.apply(UriComponentsBuilder.newInstance());
            return requestSpec;
        });
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(TopArtistsResponse.class)).thenReturn(Mono.just(response));

        TopArtistsResponse result = client.getTopArtists("medium_term", 5, 0);

        assertSame(response, result);
    }

    @Test
    void getUserPlaylistsUsesPlaylistsEndpoint() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        CurrentUserPlaylistsResponse response = new CurrentUserPlaylistsResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
            uriFunction.apply(UriComponentsBuilder.newInstance());
            return requestSpec;
        });
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(CurrentUserPlaylistsResponse.class)).thenReturn(Mono.just(response));

        CurrentUserPlaylistsResponse result = client.getUserPlaylists(5, 1);

        assertSame(response, result);
    }

    @Test
    void getUserSavedTracksUsesTracksEndpoint() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        CurrentUserSavedTracksResponse response = new CurrentUserSavedTracksResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
            uriFunction.apply(UriComponentsBuilder.newInstance());
            return requestSpec;
        });
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(CurrentUserSavedTracksResponse.class)).thenReturn(Mono.just(response));

        CurrentUserSavedTracksResponse result = client.getUserSavedTracks(10, 2);

        assertSame(response, result);
    }

    @Test
    void getUserFollowedArtistsUsesFollowingEndpoint() {
        UserClient client = new UserClient(webClient, authorizedClientManager, "localhost");
        configureAuthenticatedClient("spotify-token");
        CurrentUserFollowedArtistsResponse response = new CurrentUserFollowedArtistsResponse();

        WebClient.RequestBodyUriSpec requestSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        lenient().when(webClient.method(any(HttpMethod.class))).thenReturn(requestSpec);
        lenient().when(requestSpec.uri(any(Function.class))).thenAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
            uriFunction.apply(UriComponentsBuilder.newInstance());
            return requestSpec;
        });
        lenient().when(requestSpec.headers(any(Consumer.class))).thenReturn(requestBodySpec);
        lenient().when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        lenient().when(responseSpec.bodyToMono(CurrentUserFollowedArtistsResponse.class)).thenReturn(Mono.just(response));

        CurrentUserFollowedArtistsResponse result = client.getUserFollowedArtists(15);

        assertSame(response, result);
    }

    private void configureAuthenticatedClient(String tokenValue) {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken());
        OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);
        lenient().when(accessToken.getTokenValue()).thenReturn(tokenValue);
        OAuth2AuthorizedClient authorizedClient = authorizedClient(accessToken);
        lenient().when(authorizedClientManager.authorize(any(OAuth2AuthorizeRequest.class))).thenReturn(authorizedClient);
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