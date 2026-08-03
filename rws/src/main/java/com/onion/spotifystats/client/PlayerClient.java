package com.onion.spotifystats.client;

import com.onion.spotifystats.dto.request.player.PlayRequestDTO;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.PlayerState;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PlayerClient extends SpotifyApiClient{

    public PlayerClient(WebClient webClient, OAuth2AuthorizedClientManager authorizedClientManager,
                                    @Value("${spotify.api.base-url}") String apiUrl) {
        super(webClient, authorizedClientManager, apiUrl);
    }

    public PlayerState currentlyPlaying() {
        return this.get(apiURL + "/me/player", PlayerState.class);
    }

    public RecentlyPlayedResponse recently(int limit) {
        return get(uriBuilder ->
                uriBuilder
                        .path("/me/player/recently-played")
                        .queryParam("limit", limit)
                        .build(), RecentlyPlayedResponse.class);

    }

    public void play (PlayRequestDTO body) {
        put(apiURL + "/me/player/play", body, Void.class);
    }

    public void pause(){
        put( apiURL + "/me/player/pause", null, Void.class);
    }

    public void next(){
        post( apiURL + "/me/player/next", null, Void.class);
    }

    public void previous(){
        post( apiURL + "/me/player/previous", null, Void.class);
    }

    public void volume(int percent){
        put( uriBuilder ->
                uriBuilder
                        .path("/me/player/volume")
                        .queryParam("volume_percent", percent)
                        .build(), null, Void.class);
    }
}
