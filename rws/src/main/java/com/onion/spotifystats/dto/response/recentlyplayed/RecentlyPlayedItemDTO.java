package com.onion.spotifystats.dto.response.recentlyplayed;

import com.onion.spotifystats.dto.response.track.TrackDTO;


public record RecentlyPlayedItemDTO (
        TrackDTO track,
        String playedAt
){}
