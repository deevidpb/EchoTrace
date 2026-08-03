package com.onion.spotifystats.model.spotify.response.artist;


import com.onion.spotifystats.model.spotify.response.utils.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArtistComplex extends Artist {
    private List<Image> images;
}
