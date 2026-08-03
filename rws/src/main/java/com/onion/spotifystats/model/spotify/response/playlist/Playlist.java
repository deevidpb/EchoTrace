package com.onion.spotifystats.model.spotify.response.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import com.onion.spotifystats.model.spotify.response.utils.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Playlist {
    String id;
    String name;
    String description;
    boolean collaborative;

    @JsonProperty("external_urls")
    ExternalUrls externalUrls;

    List<Image> images;

    @JsonProperty("public")
    boolean isPublic;

    User owner;
    PlaylistItems items;
}
