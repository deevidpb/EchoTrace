package com.onion.spotifystats.model.spotify.response.recentlyplayed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecentlyPlayedItem {
    private Track track;

    @JsonProperty("played_at")
    private String playedAt;
}
