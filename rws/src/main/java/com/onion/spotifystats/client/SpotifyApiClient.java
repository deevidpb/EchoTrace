package com.onion.spotifystats.client;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.net.URI;
import java.util.function.Function;
import org.springframework.http.HttpMethod;

@Component
public class SpotifyApiClient {

    private final WebClient webClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    protected final String apiURL;

    public SpotifyApiClient(WebClient webClient,
                            OAuth2AuthorizedClientManager authorizedClientManager,
                            @Value("${spotify.api.base-url}") String apiUrl) {
        this.webClient = webClient;
        this.authorizedClientManager = authorizedClientManager;
        this.apiURL = apiUrl;
    }

    private String getAccessToken() {
        OAuth2AuthenticationToken auth = getAuth();
        if (auth == null) {
            throw new IllegalStateException("Authentication not founded");
        }

        OAuth2AuthorizeRequest authorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId(
                                auth.getAuthorizedClientRegistrationId())
                        .principal(auth)
                        .build();

        OAuth2AuthorizedClient client =
                authorizedClientManager.authorize(authorizeRequest);

        return getString(client);
    }

    private static @NonNull String getString(OAuth2AuthorizedClient client) {
        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException(
                    "No OAuth2 token found. User not authenticated with Spotify."
            );
        }

        OAuth2AccessToken accessToken = client.getAccessToken();

        if (accessToken == null) {
            throw new IllegalStateException("No OAuth2 token found.");
        }

        String token = accessToken.getTokenValue();

        if (token == null) {
            throw new IllegalStateException("OAuth2 token value is null.");
        }
        return token;
    }

    private OAuth2AuthenticationToken getAuth() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (OAuth2AuthenticationToken) authentication;
    }


    private <T> T request(HttpMethod method, String uri, Class<T> responseType) {
        WebClient.ResponseSpec  request  = webClient
                .method(method)
                .uri(uri)
                .headers(h -> h.setBearerAuth(getAccessToken()))
                .retrieve();
        if (Void.class.equals(responseType)) {
            request.toBodilessEntity().block();
            return null;
        }

        return request.bodyToMono(responseType).block();
    }

    private <T> T request(HttpMethod method, Function<UriBuilder, URI> uriFunction, Class<T> responseType) {
        WebClient.ResponseSpec  request  = webClient
                .method(method)
                .uri(uriFunction)
                .headers(h -> h.setBearerAuth(getAccessToken()))
                .retrieve();
        if (Void.class.equals(responseType)) {
            request.toBodilessEntity().block();
            return null;
        }

        return request.bodyToMono(responseType).block();
    }

    private <T> T request(HttpMethod method, String uri, Object body, Class<T> responseType) {
        WebClient.ResponseSpec request;

        if (body == null) {
            request =  webClient
                    .method(method)
                    .uri(uri)
                    .headers(h -> h.setBearerAuth(getAccessToken()))
                    .retrieve();
        }
        else {
            request =  webClient
                    .method(method)
                    .uri(uri)
                    .headers(h -> h.setBearerAuth(getAccessToken()))
                    .bodyValue(body)
                    .retrieve();
        }

        if (Void.class.equals(responseType)) {
            request.toBodilessEntity().block();
            return null;
        }

        return request.bodyToMono(responseType).block();
    }

    private <T> T request(HttpMethod method, Function<UriBuilder, URI> uriFunction, Object body, Class<T> responseType) {
        WebClient.ResponseSpec request;

        if (body == null) {
            request =  webClient
                    .method(method)
                    .uri(uriFunction)
                    .headers(h -> h.setBearerAuth(getAccessToken()))
                    .retrieve();
        }
        else {
            request =  webClient
                    .method(method)
                    .uri(uriFunction)
                    .headers(h -> h.setBearerAuth(getAccessToken()))
                    .bodyValue(body)
                    .retrieve();
        }

        if (Void.class.equals(responseType)) {
            request.toBodilessEntity().block();
            return null;
        }

        return request.bodyToMono(responseType).block();
    }


    //HTTP METHODS

    protected <T> T get(String uri, Class<T> responseType){
        return request(HttpMethod.GET, uri, responseType);
    }

    protected <T> T get(Function<UriBuilder, URI> uriFunction, Class<T> responseType){
        return request(HttpMethod.GET, uriFunction, responseType);
    }

    protected <T> T post(String uri, Object body, Class<T> responseType){
        return request(HttpMethod.POST, uri, body, responseType);
    }

    protected <T> T put(String uri, Object body, Class<T> responseType){
        return request(HttpMethod.PUT, uri, body, responseType);
    }

    protected <T> T put(Function<UriBuilder, URI> uriFunction, Object body, Class<T> responseType){
        return request(HttpMethod.PUT, uriFunction, body, responseType);
    }

}
