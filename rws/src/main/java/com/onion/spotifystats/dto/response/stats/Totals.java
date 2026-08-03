package com.onion.spotifystats.dto.response.stats;

public record Totals(
        int nPlaylists,
        int savedTracks,
        int followed
) {}
