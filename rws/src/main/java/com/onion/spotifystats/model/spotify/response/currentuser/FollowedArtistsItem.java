package com.onion.spotifystats.model.spotify.response.currentuser;

import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FollowedArtistsItem {
    private int total;
    private List<ArtistComplex> items;
}
