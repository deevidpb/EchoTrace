package com.onion.spotifystats.dto.response.currentuser;

import com.onion.spotifystats.dto.response.track.TrackDTO;

public record SavedTracksItemDTO (
        String added_at,
        TrackDTO track
){
}
