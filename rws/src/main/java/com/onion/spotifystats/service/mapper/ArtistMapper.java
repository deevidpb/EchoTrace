package com.onion.spotifystats.service.mapper;

import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.artist.ArtistDTO;
import com.onion.spotifystats.dto.response.utils.ImageDTO;
import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import com.onion.spotifystats.model.spotify.response.artist.Artist;

public class ArtistMapper {
    private ArtistMapper() {}

    public static ArtistComplexDTO map (ArtistComplex artist){
        return new ArtistComplexDTO(
                artist.getId(),
                artist.getName(),
                artist.getExternalUrls().getSpotify(),
                artist.getImages().stream().map(image -> new ImageDTO(
                        image.getUrl(),
                        image.getHeight(),
                        image.getWidth()
                )).toList(),
                0,
                false
        );
    }

    public static ArtistDTO map (Artist artist){
        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getExternalUrls().getSpotify()
        );
    }
}
