package com.onion.spotifystats.client;

import com.onion.spotifystats.dto.request.player.PlayRequestDTO;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.PlayerState;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PlayerClient extends SpotifyApiClient{

    public PlayerClient(WebClient webClient, OAuth2AuthorizedClientManager authorizedClientManager) {
        super(webClient, authorizedClientManager);
    }

    public PlayerState currentlyPlaying() {
        return this.get(API_URL + "/me/player", PlayerState.class);
    }

    public RecentlyPlayedResponse recently(int limit) {
        return get(uriBuilder ->
                uriBuilder
                        .path("/me/player/recently-played")
                        .queryParam("limit", limit)
                        .build(), RecentlyPlayedResponse.class);

    }

    public void play (PlayRequestDTO body) {
        put(API_URL + "/me/player/play", body, Void.class);
    }

    public void pause(){
        put( API_URL + "/me/player/pause", null, Void.class);
    }

    public void next(){
        post( API_URL + "/me/player/next", null, Void.class);
    }

    public void previous(){
        post( API_URL + "/me/player/previous", null, Void.class);
    }

    public void volume(int percent){
        put( uriBuilder ->
                uriBuilder
                        .path("/me/player/volume")
                        .queryParam("volume_percent", percent)
                        .build(), null, Void.class);
    }
}
