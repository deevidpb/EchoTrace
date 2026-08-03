package com.onion.spotifystats.dto.response.stats;

import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.currentuser.SpotifyUserDTO;
import com.onion.spotifystats.dto.response.recentlyplayed.RecentlyPlayedItemDTO;
import com.onion.spotifystats.dto.response.track.TrackDTO;

import java.util.List;

public record StatsSnapshotDTO (

        SpotifyUserDTO user,

        List<TrackDTO> topTracks,

        List<ArtistComplexDTO> topArtists,

        List<RecentlyPlayedItemDTO> recentlyPlayed,

        List<AlbumDTO> topAlbums,

        ArtistComplexDTO breakthroughArtist,

        Totals totals


) {}