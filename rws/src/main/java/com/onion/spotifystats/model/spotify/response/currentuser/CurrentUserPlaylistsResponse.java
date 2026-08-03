package com.onion.spotifystats.model.spotify.response.currentuser;

import com.onion.spotifystats.model.spotify.response.playlist.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserPlaylistsResponse {
    int total;
    List<Playlist> items;

}
