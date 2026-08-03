package com.onion.spotifystats.model.spotify.response.track;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.album.Album;
import com.onion.spotifystats.model.spotify.response.artist.Artist;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Track {
    private String id;
    private String name;
    private Album album;
    private List<Artist> artists;

    @JsonProperty("duration_ms")
    private int durationMs;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
