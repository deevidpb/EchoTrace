package com.onion.spotifystats.model.spotify.response.topartists;

import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopArtistsResponse {
    private List<ArtistComplex> items;
}
