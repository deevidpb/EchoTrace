package com.onion.spotifystats.dto.request.player;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlayRequestDTO(
        @JsonProperty("context_uri")
        String contextUri,

        @JsonProperty("uris")
        List<String> uris,

        @JsonProperty("position_ms")
        Integer positionMs
)
{}
