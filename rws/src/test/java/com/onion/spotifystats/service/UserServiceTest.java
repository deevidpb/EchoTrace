package com.onion.spotifystats.service;

import com.onion.spotifystats.TestData;
import com.onion.spotifystats.client.UserClient;
import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.currentuser.*;
import com.onion.spotifystats.dto.response.playlist.PlayListDTO;
import com.onion.spotifystats.dto.response.playlist.UserDTO;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.dto.response.utils.ImageDTO;
import com.onion.spotifystats.model.spotify.response.currentuser.*;
import com.onion.spotifystats.model.spotify.response.topartists.TopArtistsResponse;
import com.onion.spotifystats.model.spotify.response.toptracks.TopTracksResponse;
import com.onion.spotifystats.service.mapper.ArtistMapper;
import com.onion.spotifystats.service.mapper.TrackMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserService userService;

    @Test
    void me() {
        when(userClient.getCurrentUser()).thenReturn(TestData.mockCurrentUserResponse());

        SpotifyUserDTO dto = userService.getMe();

        assertNotNull(dto);
        assertEquals("user_id", dto.id());
        assertEquals("display_name", dto.name());
        assertEquals("email", dto.email());
        assertEquals(0, dto.followers());
        assertEquals("spotify", dto.url());
        assertNotNull(dto.images());
        assertEquals(1, dto.images().size());
        assertEquals(100, dto.images().getFirst().height());
        assertEquals(100, dto.images().getFirst().width());
        assertEquals("image-url", dto.images().getFirst().url());
    }

    @Test
    void topArtistsShort(){
        TopArtistsResponse artistsResponse = TestData.mockTopArtistsResponse();
        TopArtistsResponse responseEmpty = TestData.mockTopArtistsResponseEmpty();

        when(userClient.getTopArtists("short_term", 1,0)).thenReturn(artistsResponse);
        when(userClient.getTopArtists("short_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopArtists("short_term", 1,2)).thenReturn(responseEmpty);

        when(userClient.getTopArtists("medium_term", 1,0)).thenReturn(artistsResponse);
        when(userClient.getTopArtists("medium_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopArtists("medium_term", 1,2)).thenReturn(responseEmpty);

        ArtistComplexDTO source = ArtistMapper.map(TestData.mockArtistComplex());
        ArtistComplexDTO artistDto = new ArtistComplexDTO(source.id(), source.name(),
                source.url(), source.images(), 0, false);


        List<ArtistComplexDTO> dto = userService.topArtists("short_term", 1);
        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(artistDto, dto.getFirst());
    }

    @Test
    void topArtistsMedium(){
        TopArtistsResponse artistsResponse = TestData.mockTopArtistsResponse();
        TopArtistsResponse responseEmpty = TestData.mockTopArtistsResponseEmpty();

        when(userClient.getTopArtists("medium_term", 1,0)).thenReturn(artistsResponse);
        when(userClient.getTopArtists("medium_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopArtists("medium_term", 1,2)).thenReturn(responseEmpty);

        ArtistComplexDTO artistDto = ArtistMapper.map(TestData.mockArtistComplex());

        List<ArtistComplexDTO> dto =userService.topArtists("medium_term", 1);
        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(artistDto, dto.getFirst());
    }

    @Test
    void artistaRevelacion(){
        List<ArtistComplexDTO> artists = new ArrayList<>();

        ImageDTO img = new ImageDTO("image-url", 100, 100);

        List<ImageDTO> images = new ArrayList<>();
        images.add(img);

        ArtistComplexDTO artist = new ArtistComplexDTO("id",
                "name", "url", images, 1, false);
        ArtistComplexDTO artist2 = new ArtistComplexDTO("id2",
                "name2", "url2", images, 1, true);

        artists.add(artist);
        artists.add(artist2);

        ArtistComplexDTO dto = userService.getArtistaRevelacion(artists);

        assertNotNull(dto);
        assertEquals(artist2, dto);
    }

    @Test
    void topTracksShort(){

        TopTracksResponse tracksResponse =  TestData.mockTopTracksResponse();
        TopTracksResponse responseEmpty = TestData.mockTopTracksResponseEmpty();


        when(userClient.getTopTracks("short_term", 1,0)).thenReturn(tracksResponse);
        when(userClient.getTopTracks("short_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopTracks("short_term", 1,2)).thenReturn(responseEmpty);

        when(userClient.getTopTracks("medium_term", 1,0)).thenReturn(tracksResponse);
        when(userClient.getTopTracks("medium_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopTracks("medium_term", 1,2)).thenReturn(responseEmpty);

        TrackDTO source = TrackMapper.map(TestData.mockTrack());
        TrackDTO trackDto = new TrackDTO(source.id(), source.name(), source.durationMs(),
                source.album(), source.artists(), source.url(), 0, false);


        List<TrackDTO> dto = userService.topTracks("short_term", 1);
        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(trackDto, dto.getFirst());
    }

    @Test
    void topTracksMedium(){
        TopTracksResponse tracksResponse =  TestData.mockTopTracksResponse();
        TopTracksResponse responseEmpty = TestData.mockTopTracksResponseEmpty();

        when(userClient.getTopTracks("medium_term", 1,0)).thenReturn(tracksResponse);
        when(userClient.getTopTracks("medium_term", 1,1)).thenReturn(responseEmpty);
        when(userClient.getTopTracks("medium_term", 1,2)).thenReturn(responseEmpty);

        TrackDTO trackDto = TrackMapper.map(TestData.mockTrack());

        List<TrackDTO> dto = userService.topTracks("medium_term", 1);
        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(trackDto, dto.getFirst());
    }

    @Test
    void topAlbum(){
        List<TrackDTO> tracks = new ArrayList<>();
        TrackDTO trackDto = TrackMapper.map(TestData.mockTrack());
        tracks.add(trackDto);

        List<AlbumDTO> dto = userService.topAlbums(tracks, 1);
        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(trackDto.album(), dto.getFirst());
    }

    @Test
    void currentUserPlaylists(){
        when(userClient.getUserPlaylists(1, 0)).thenReturn(TestData.mockCurrentUserPlaylistsResponse());

        CurrentUserPlaylistsDTO dto = userService.getCurrentUserPlaylists(1);

        assertNotNull(dto);
        assertEquals(1, dto.total());
        assertEquals(1, dto.items().size());
        PlayListDTO playListDto = dto.items().getFirst();
        assertNotNull(playListDto);
        assertEquals("playlist-id", playListDto.id());
        assertEquals("playlist-name", playListDto.name());
        assertEquals("playlist-description", playListDto.description());
        assertFalse(playListDto.collaborative());
        assertTrue(playListDto.isPublic());
        assertEquals("url", playListDto.spotifyUrl());
        ImageDTO imageDto =  playListDto.images().getFirst();
        assertNotNull(imageDto);
        assertEquals("image-url", imageDto.url());
        assertEquals(100, imageDto.width());
        assertEquals(100, imageDto.height());
        assertEquals(1, playListDto.nItems());
        UserDTO userDto = playListDto.user();
        assertNotNull(userDto);
        assertEquals("user-id", userDto.id());
        assertEquals("user-name", userDto.name());
        assertEquals("url", userDto.userUrl());
    }

    @Test
    void currentUserSavedTracks(){
        when(userClient.getUserSavedTracks(1, 0)).thenReturn(TestData.mockCurrentUserSavedTracksResponse());

        CurrentUserSavedTracksDTO dto =  userService.getCurrentUserSavedTracks(1);

        assertNotNull(dto);
        assertEquals(1, dto.total());
        SavedTracksItemDTO itemDto = dto.tracks().getFirst();
        assertNotNull(itemDto);
        assertEquals("addedAt", itemDto.added_at());
        assertEquals(TrackMapper.map(TestData.mockTrack()), itemDto.track());
    }

    @Test
    void currentUserFollowedArtists(){
        CurrentUserFollowedArtistsResponse response =   TestData.mockCurrentUserFollowedArtistsResponse();

        when(userClient.getUserFollowedArtists(1)).thenReturn(response);

        CurrentUserFollowedArtistsDTO dto = userService.getCurrentUserFollowedArtists(1);
        assertNotNull(dto);
        assertEquals(1, dto.total());
        assertNotNull(dto.artists());
        assertEquals(ArtistMapper.map(response.getArtists().getItems().getFirst()),  dto.artists().getFirst());
    }
}
