package com.onion.spotifystats.dto.response.currentlyplaying;

import com.onion.spotifystats.dto.response.track.TrackDTO;

public record PlayerStateDTO(
        boolean isPlaying,
        TrackDTO track,
        int progressMs,
        int durationMs,
        int volume,
        String repeatMode,
        boolean shuffleState
) {}
