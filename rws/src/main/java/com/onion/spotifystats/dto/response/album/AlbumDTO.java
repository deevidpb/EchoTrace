package com.onion.spotifystats.dto.response.album;

import com.onion.spotifystats.dto.response.artist.ArtistDTO;
import com.onion.spotifystats.dto.response.utils.ImageDTO;

import java.util.List;

public record AlbumDTO (
    String id,
    String name,
    int totalTracks,
    List<ImageDTO> images,
    String url,
    List<ArtistDTO> artists
) {}
