package com.onion.spotifystats.client;

import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserFollowedArtistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserPlaylistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserSavedTracksResponse;
import com.onion.spotifystats.model.spotify.response.topartists.TopArtistsResponse;
import com.onion.spotifystats.model.spotify.response.toptracks.TopTracksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient extends SpotifyApiClient{

    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    public UserClient(WebClient webClient, OAuth2AuthorizedClientManager authorizedClientManager,
                            @Value("${spotify.api.base-url}") String apiUrl) {
        super(webClient, authorizedClientManager, apiUrl);
    }

    public CurrentUserResponse getCurrentUser() {
        return get(apiURL + "/me", CurrentUserResponse.class);
    }

    public TopTracksResponse getTopTracks(String timeRange, int limit, int offset) {
        return get(uriBuilder ->
                uriBuilder
                        .path("/me/top/tracks")
                        .queryParam("time_range", timeRange)
                        .queryParam(LIMIT, limit)
                        .queryParam(OFFSET, offset)
                        .build(), TopTracksResponse.class);
    }

    public TopArtistsResponse getTopArtists(String timeRange, int limit, int offset) {
        return get(uriBuilder ->
                        uriBuilder
                                .path("/me/top/artists")
                                .queryParam("time_range", timeRange)
                                .queryParam(LIMIT, limit)
                                .queryParam(OFFSET, offset)
                                .build(),
                TopArtistsResponse.class);
    }

    public CurrentUserPlaylistsResponse getUserPlaylists(int limit, int offset) {
        return get(uriBuilder ->
                        uriBuilder
                                .path("/me/playlists")
                                .queryParam(LIMIT, limit)
                                .queryParam(OFFSET, offset)
                                .build(),
                CurrentUserPlaylistsResponse.class);
    }

    public CurrentUserSavedTracksResponse getUserSavedTracks (int limit, int offset){
        return get(uriBuilder ->
                        uriBuilder
                                .path("/me/tracks")
                                .queryParam(LIMIT, limit)
                                .queryParam(OFFSET, offset)
                                .build(),
                CurrentUserSavedTracksResponse.class);
    }

    public CurrentUserFollowedArtistsResponse getUserFollowedArtists (int limit){
        return get(uriBuilder ->
                        uriBuilder
                                .path("/me/following")
                                .queryParam("type", "artist")
                                .queryParam(LIMIT, limit)
                                .build(),
                CurrentUserFollowedArtistsResponse.class);
    }
}
