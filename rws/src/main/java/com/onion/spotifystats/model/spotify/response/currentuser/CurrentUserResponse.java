package com.onion.spotifystats.model.spotify.response.currentuser;

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
public class CurrentUserResponse {
    private String id;

    @JsonProperty("display_name")
    private String displayName;

    private String email;
    private List<Image> images;
    private Followers followers;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
