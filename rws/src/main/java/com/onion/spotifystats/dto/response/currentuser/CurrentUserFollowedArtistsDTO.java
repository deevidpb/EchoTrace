package com.onion.spotifystats.dto.response.currentuser;

import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;

import java.util.List;

public record CurrentUserFollowedArtistsDTO(
        int total,
        List<ArtistComplexDTO> artists
) {
}
