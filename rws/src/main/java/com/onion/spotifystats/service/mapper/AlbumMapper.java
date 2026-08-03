package com.onion.spotifystats.service.mapper;

import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.model.spotify.response.album.Album;

public class AlbumMapper {
    private AlbumMapper() {}

    public static AlbumDTO map (Album album) {
        return new AlbumDTO(
                album.getId(),
                album.getName(),
                album.getTotalTracks(),
                album.getImages().stream().map(ImageMapper::map).toList(),
                album.getExternalUrls().getSpotify(),
                album.getArtists().stream().map(ArtistMapper::map).toList()
        );
    }
}
