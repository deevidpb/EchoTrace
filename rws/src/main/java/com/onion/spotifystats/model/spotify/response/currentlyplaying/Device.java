package com.onion.spotifystats.model.spotify.response.currentlyplaying;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Device {
    private String name;
    private String type;
    @JsonProperty("volume_percent")
    private int volumePercent;
}
