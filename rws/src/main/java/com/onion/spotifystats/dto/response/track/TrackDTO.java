package com.onion.spotifystats.dto.response.track;

import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistDTO;

import java.util.List;

public record TrackDTO(
        String id,
        String name,
        int durationMs,
        AlbumDTO album,
        List<ArtistDTO> artists,
        String url,
        int rankChange,
        boolean isNew
) {}
