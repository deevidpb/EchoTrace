package com.onion.spotifystats.model.spotify.response.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Artist {
    private String id;
    private String name;
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
