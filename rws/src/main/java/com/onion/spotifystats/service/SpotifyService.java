package com.onion.spotifystats.service;

import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.currentuser.CurrentUserFollowedArtistsDTO;
import com.onion.spotifystats.dto.response.currentuser.CurrentUserPlaylistsDTO;
import com.onion.spotifystats.dto.response.currentuser.CurrentUserSavedTracksDTO;
import com.onion.spotifystats.dto.response.stats.StatsSnapshotDTO;
import com.onion.spotifystats.dto.response.stats.Totals;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpotifyService {

    private final PlayerService playerService;
    private final UserService userService;

    public SpotifyService(PlayerService playerService,
                          UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }

    public StatsSnapshotDTO stats( String timeRange, int topLimit, int recentLimit) {
        List<ArtistComplexDTO> artists = userService.topArtists(timeRange, topLimit);
        List<TrackDTO> tracks = userService.topTracks(timeRange, topLimit);
        ArtistComplexDTO artist = userService.getArtistaRevelacion(artists);
        CurrentUserPlaylistsDTO playlists = userService.getCurrentUserPlaylists(1);
        CurrentUserSavedTracksDTO savedTracks = userService.getCurrentUserSavedTracks(1);
        CurrentUserFollowedArtistsDTO followedArtists =
                userService.getCurrentUserFollowedArtists(1);

        return new StatsSnapshotDTO(
                userService.getMe(),
                tracks,
                artists,
                playerService.recently(recentLimit),
                userService.topAlbums(tracks, 6),
                artist,
                new Totals(playlists.total(),savedTracks.total(),
                        followedArtists.total())
        );
    }

}