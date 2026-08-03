package com.onion.spotifystats.model.spotify.response.album;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.artist.Artist;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import com.onion.spotifystats.model.spotify.response.utils.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Album {
    private String id;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private int totalTracks;
    private List<Image> images;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;

    private List<Artist> artists;
}


