package com.onion.spotifystats.model.spotify.response.currentlyplaying;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerState {
    private Device device;

    @JsonProperty("shuffle_state")
    private boolean shuffleState;

    @JsonProperty("is_playing")
    private boolean isPlaying;

    @JsonProperty("progress_ms")
    private int progressMs;

    private Track item;

    @JsonProperty("currently_playing_type")
    private String currentlyPlayingType;

    @JsonProperty("repeat_state")
    private String repeatState;
}
