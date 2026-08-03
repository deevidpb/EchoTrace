package com.onion.spotifystats.model.spotify.response.toptracks;

import com.onion.spotifystats.model.spotify.response.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopTracksResponse {
    private List<Track> items;
}
