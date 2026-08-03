package com.onion.spotifystats.model.spotify.response.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @JsonProperty("external_urls")
    ExternalUrls externalUrls;

    @JsonProperty("display_name")
    String displayName;
    String id;
}
