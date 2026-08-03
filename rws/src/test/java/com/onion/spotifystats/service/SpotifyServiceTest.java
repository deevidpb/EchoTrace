package com.onion.spotifystats.service;

import com.onion.spotifystats.TestData;
import com.onion.spotifystats.dto.response.stats.StatsSnapshotDTO;
import com.onion.spotifystats.dto.response.stats.Totals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpotifyServiceTest {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private UserService userService;

    @Mock
    private PlayerService playerService;

    @Test
    void stats(){
        String timeRange = "short_term";
        int topLimit = 1;
        int recentLimit = 1;

        when(userService.topArtists(timeRange, topLimit)).thenReturn(TestData.mockArtistComplexDTOList());
        when(userService.topTracks(timeRange, topLimit)).thenReturn(TestData.mockTracksDTOList());
        when(userService.getArtistaRevelacion(TestData.mockArtistComplexDTOList())).thenReturn(TestData.mockArtistComplexDTO());
        when(userService.getCurrentUserPlaylists(1)).thenReturn(TestData.mockCurrentUserPlaylistsDTO());
        when(userService.getCurrentUserSavedTracks(1)).thenReturn(TestData.mockCurrentUserSavedTracksDTO());
        when(userService.getCurrentUserFollowedArtists(1)).thenReturn(TestData.mockCurrentUserFollowedArtistsDTO());
        when(userService.getMe()).thenReturn(TestData.mockSpotifyUserDTO());
        when(playerService.recently(1)).thenReturn(TestData.mockRecentlyPlayedItemDTOList());
        when(userService.topAlbums(TestData.mockTracksDTOList(), 6)).thenReturn(TestData.mockAlbumDTOList());

        StatsSnapshotDTO dto = spotifyService.stats(timeRange, topLimit, recentLimit);
        assertNotNull(dto);
        assertEquals(TestData.mockSpotifyUserDTO(), dto.user());
        assertEquals(TestData.mockTracksDTOList(), dto.topTracks());
        assertEquals(TestData.mockArtistComplexDTOList(), dto.topArtists());
        assertEquals(TestData.mockRecentlyPlayedItemDTOList(), dto.recentlyPlayed());
        assertEquals(TestData.mockAlbumDTOList(), dto.topAlbums());
        assertEquals(TestData.mockArtistComplexDTO(), dto.breakthroughArtist());
        assertNotNull(dto.totals());
        Totals totals = dto.totals();
        assertEquals(1, totals.nPlaylists());
        assertEquals(1, totals.savedTracks());
        assertEquals(1, totals.followed());
    }
}
