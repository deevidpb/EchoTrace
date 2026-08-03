package com.onion.spotifystats.service.mapper;

import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.model.spotify.response.track.Track;
import com.onion.spotifystats.service.mapper.ArtistMapper.*;

public class TrackMapper {
    private TrackMapper() {}

    public static TrackDTO map(Track track){
        return new TrackDTO(
                track.getId(),
                track.getName(),
                track.getDurationMs(),
                AlbumMapper.map(track.getAlbum()),
                track.getArtists().stream().map(ArtistMapper::map).toList(),
                track.getExternalUrls().getSpotify(),
                0,
                false
        );
    }
}
