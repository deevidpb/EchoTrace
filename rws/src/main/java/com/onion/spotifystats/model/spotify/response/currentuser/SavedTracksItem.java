package com.onion.spotifystats.model.spotify.response.currentuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavedTracksItem {
    @JsonProperty("addedAt")
    String addedAt;
    Track track;
}
