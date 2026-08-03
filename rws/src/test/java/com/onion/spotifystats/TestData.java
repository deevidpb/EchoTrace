package com.onion.spotifystats;

import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.artist.ArtistDTO;
import com.onion.spotifystats.dto.response.currentlyplaying.PlayerStateDTO;
import com.onion.spotifystats.dto.response.currentuser.*;
import com.onion.spotifystats.dto.response.playlist.PlayListDTO;
import com.onion.spotifystats.dto.response.playlist.UserDTO;
import com.onion.spotifystats.dto.response.recentlyplayed.RecentlyPlayedItemDTO;
import com.onion.spotifystats.dto.response.stats.StatsSnapshotDTO;
import com.onion.spotifystats.dto.response.stats.Totals;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.dto.response.utils.ImageDTO;
import com.onion.spotifystats.model.spotify.response.album.Album;
import com.onion.spotifystats.model.spotify.response.artist.Artist;
import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.Device;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.PlayerState;
import com.onion.spotifystats.model.spotify.response.currentuser.*;
import com.onion.spotifystats.model.spotify.response.playlist.Playlist;
import com.onion.spotifystats.model.spotify.response.playlist.PlaylistItems;
import com.onion.spotifystats.model.spotify.response.playlist.User;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedItem;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedResponse;
import com.onion.spotifystats.model.spotify.response.topartists.TopArtistsResponse;
import com.onion.spotifystats.model.spotify.response.toptracks.TopTracksResponse;
import com.onion.spotifystats.model.spotify.response.track.Track;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import com.onion.spotifystats.model.spotify.response.utils.Image;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    //MODELS

    public static Track mockTrack(){
        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);

        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify-url");

        Artist artist1 = new Artist();
        artist1.setName("spotify-artist-1");
        artist1.setId("artist1-id");
        ExternalUrls externalUrls1 = new ExternalUrls();
        externalUrls1.setSpotify("spotify-url-artist-1");
        artist1.setExternalUrls(externalUrls1);

        Artist artist2 = new Artist();
        artist2.setName("spotify-artist-2");
        artist2.setId("artist2-id");
        ExternalUrls externalUrls2 = new ExternalUrls();
        externalUrls2.setSpotify("spotify-url-artist-2");
        artist2.setExternalUrls(externalUrls2);

        List<Artist> artists = new ArrayList<>();
        artists.add(artist1);
        artists.add(artist2);


        Album album = new Album();
        album.setId("album-id");
        album.setName("album-name");
        album.setReleaseDate("album-relase-date");
        album.setReleaseDatePrecision("precision");
        album.setTotalTracks(0);
        album.setImages(images);
        album.setExternalUrls(externalUrls);
        album.setArtists(artists);

        Artist artist3 = new Artist();
        artist3.setName("spotify-artist-3");
        artist3.setId("artist3-id");
        ExternalUrls externalUrls3 = new ExternalUrls();
        externalUrls3.setSpotify("spotify-url-artist-3");
        artist3.setExternalUrls(externalUrls3);

        Artist artist4 = new Artist();
        artist4.setName("spotify-artist-4");
        artist4.setId("artist4-id");
        ExternalUrls externalUrls4 = new ExternalUrls();
        externalUrls4.setSpotify("spotify-url-artist-4");
        artist4.setExternalUrls(externalUrls4);

        List<Artist> artists2 = new ArrayList<>();
        artists2.add(artist3);
        artists2.add(artist4);

        Track track = new Track();
        track.setId("track-id");
        track.setName("track-name");
        track.setAlbum(album);
        track.setArtists(artists2);
        track.setDurationMs(0);
        track.setExternalUrls(externalUrls);

        return track;
    }

    public static ArtistComplex mockArtistComplex(){
        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify");

        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);

        ArtistComplex artistComplex = new ArtistComplex();
        artistComplex.setId("artist_id");
        artistComplex.setName("artist_name");
        artistComplex.setImages(images);
        artistComplex.setExternalUrls(externalUrls);

        return artistComplex;
    }

    public static Artist mockArtist(){
        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify");

        Artist artist = new Artist();
        artist.setId("artist_id");
        artist.setName("artist_name");
        artist.setExternalUrls(externalUrls);

        return artist;
    }

    public static PlayerState mockPlayerState(){
        PlayerState playerState = new PlayerState();
        Device device = new Device();
        device.setName("device");
        device.setType("device-type");
        device.setVolumePercent(0);
        playerState.setDevice(device);
        playerState.setShuffleState(false);
        playerState.setPlaying(true);
        playerState.setProgressMs(0);
        playerState.setItem(mockTrack());
        playerState.setCurrentlyPlayingType("playing-type");
        playerState.setRepeatState("off");
        return playerState;
    }

    public static RecentlyPlayedResponse mockRecentlyPlayedResponse(){
        RecentlyPlayedResponse response = new RecentlyPlayedResponse();
        RecentlyPlayedItem item = new RecentlyPlayedItem();
        item.setTrack(mockTrack());
        item.setPlayedAt("played-at");
        List<RecentlyPlayedItem> items = new ArrayList<>();
        items.add(item);
        response.setItems(items);

        return response;
    }

    public static CurrentUserResponse  mockCurrentUserResponse(){
        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);

        Followers followers = new Followers();
        followers.setTotal(0);

        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify");


        CurrentUserResponse response = new CurrentUserResponse();
        response.setId("user_id");
        response.setDisplayName("display_name");
        response.setEmail("email");
        response.setImages(images);
        response.setFollowers(followers);
        response.setExternalUrls(externalUrls);

        return response;
    }

    public static TopArtistsResponse mockTopArtistsResponse(){
        TopArtistsResponse artistsResponse = new TopArtistsResponse();
        List<ArtistComplex> artists = new ArrayList<>();
        artists.add(mockArtistComplex());
        artistsResponse.setItems(artists);
        return artistsResponse;
    }

    public static TopArtistsResponse mockTopArtistsResponseEmpty(){
        TopArtistsResponse responseEmpty = new TopArtistsResponse();
        responseEmpty.setItems(new ArrayList<>());

        return  responseEmpty;
    }

    public static TopTracksResponse mockTopTracksResponse(){
        TopTracksResponse tracksResponse = new TopTracksResponse();
        List<Track> tracks = new ArrayList<>();
        tracks.add(mockTrack());
        tracksResponse.setItems(tracks);

        return  tracksResponse;
    }

    public static TopTracksResponse mockTopTracksResponseEmpty(){
        TopTracksResponse responseEmpty = new TopTracksResponse();
        responseEmpty.setItems(new ArrayList<>());

        return  responseEmpty;
    }

    public static CurrentUserPlaylistsResponse mockCurrentUserPlaylistsResponse(){
        CurrentUserPlaylistsResponse response = new  CurrentUserPlaylistsResponse();

        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("url");

        User user = new User();
        user.setExternalUrls(externalUrls);
        user.setDisplayName("user-name");
        user.setId("user-id");

        PlaylistItems playlistItems = new PlaylistItems();
        playlistItems.setTotal(1);

        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);

        Playlist playlist = new Playlist();
        playlist.setId("playlist-id");
        playlist.setName("playlist-name");
        playlist.setDescription("playlist-description");
        playlist.setCollaborative(false);
        playlist.setExternalUrls(externalUrls);
        playlist.setImages(images);
        playlist.setPublic(true);
        playlist.setOwner(user);
        playlist.setItems(playlistItems);

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist);

        response.setTotal(1);
        response.setItems(playlists);

        return response;
    }

    public static CurrentUserSavedTracksResponse mockCurrentUserSavedTracksResponse(){
        CurrentUserSavedTracksResponse response = new  CurrentUserSavedTracksResponse();

        SavedTracksItem item = new SavedTracksItem();
        item.setAddedAt("addedAt");
        item.setTrack(mockTrack());

        List<SavedTracksItem> savedTracks = new ArrayList<>();
        savedTracks.add(item);
        response.setItems(savedTracks);
        response.setTotal(1);

        return response;
    }

    public static CurrentUserFollowedArtistsResponse mockCurrentUserFollowedArtistsResponse(){
        CurrentUserFollowedArtistsResponse response = new  CurrentUserFollowedArtistsResponse();

        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("url");

        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);

        ArtistComplex artistComplex = new ArtistComplex();
        artistComplex.setId("artist-complex-id");
        artistComplex.setName("artist-complex-name");
        artistComplex.setExternalUrls(externalUrls);
        artistComplex.setImages(images);

        List<ArtistComplex> artistComplexes = new ArrayList<>();
        artistComplexes.add(artistComplex);

        FollowedArtistsItem item = new FollowedArtistsItem();
        item.setItems(artistComplexes);
        item.setTotal(1);

        response.setArtists(item);

        return response;
    }

    //DTOS
    public static List<ImageDTO> mockImageDTOList(){
        List<ImageDTO> images = new ArrayList<>();
        images.add(new ImageDTO("image-url", 100, 100));
        return images;
    }

    public static ArtistComplexDTO mockArtistComplexDTO(){
        return new ArtistComplexDTO("artist_id","artist_name", "artist_url",
                mockImageDTOList(), 0, false);
    }

    public static List<ArtistComplexDTO> mockArtistComplexDTOList(){
        ArrayList<ArtistComplexDTO> artistComplexDTOs = new ArrayList<>();
        artistComplexDTOs.add(mockArtistComplexDTO());
        return artistComplexDTOs;
    }

    public static ArtistDTO mockArtistDTO(){
        return new ArtistDTO("artist_id","artist_name", "artist_url");
    }

    public static List<ArtistDTO> mockArtistDTOList(){
        ArrayList<ArtistDTO> artistDTOs = new ArrayList<>();
        artistDTOs.add(mockArtistDTO());
        return artistDTOs;
    }

    public static AlbumDTO mockAlbumDTO(){
        return new AlbumDTO("album_id", "album-name", 1,
                mockImageDTOList(), "album-url", mockArtistDTOList());
    }

    public static TrackDTO mockTrackDTO(){
        return new TrackDTO("track-id", "track-name", 0,
                mockAlbumDTO(), mockArtistDTOList(), "track-url" , 0, false);
    }

    public static List<TrackDTO> mockTracksDTOList(){
        List<TrackDTO> trackDTOs = new ArrayList<>();
        trackDTOs.add(mockTrackDTO());
        return trackDTOs;
    }

    public static UserDTO mockUserDTO(){
        return new UserDTO("userId", "user-name", "user-email");
    }

    public static List<PlayListDTO> mockPlayListDTOList(){
        List<PlayListDTO> playListDTOs = new ArrayList<>();
        playListDTOs.add(new PlayListDTO("playlist-id", "playlist-name", "playlist-desc",
                false, true, "url", mockImageDTOList(), 1, mockUserDTO()));
        return playListDTOs;
    }

    public static CurrentUserPlaylistsDTO mockCurrentUserPlaylistsDTO(){
        return new CurrentUserPlaylistsDTO(1, mockPlayListDTOList());
    }

    public static List<SavedTracksItemDTO> mockSavedTracksItemDTOList(){
        List<SavedTracksItemDTO> savedTracksItemDTOs = new ArrayList<>();
        savedTracksItemDTOs.add(new SavedTracksItemDTO("added-at", mockTrackDTO()));
        return savedTracksItemDTOs;
    }

    public static CurrentUserSavedTracksDTO  mockCurrentUserSavedTracksDTO(){
        return new CurrentUserSavedTracksDTO(1, mockSavedTracksItemDTOList());
    }

    public static CurrentUserFollowedArtistsDTO mockCurrentUserFollowedArtistsDTO(){
        return new CurrentUserFollowedArtistsDTO(1, mockArtistComplexDTOList());
    }

    public static SpotifyUserDTO mockSpotifyUserDTO(){
        return new SpotifyUserDTO("user-id", "user-name", "user-email", 1,
                "user-url", mockImageDTOList());
    }

    public static List<AlbumDTO> mockAlbumDTOList(){
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        albumDTOs.add(mockAlbumDTO());
        return albumDTOs;
    }

    public static  List<RecentlyPlayedItemDTO>  mockRecentlyPlayedItemDTOList(){
        List<RecentlyPlayedItemDTO> recentlyPlayedItemDTOs = new ArrayList<>();
        recentlyPlayedItemDTOs.add(new RecentlyPlayedItemDTO(mockTrackDTO(), "played-at"));
        return recentlyPlayedItemDTOs;
    }

    public static Totals mockTotals(){
        return new Totals(mockCurrentUserPlaylistsDTO().total(),mockCurrentUserSavedTracksDTO().total(),
                mockCurrentUserFollowedArtistsDTO().total());
    }

    public static StatsSnapshotDTO mockStatsSnapshotDTO(){
        return new StatsSnapshotDTO(mockSpotifyUserDTO(), mockTracksDTOList(),
                mockArtistComplexDTOList(), mockRecentlyPlayedItemDTOList(),
                mockAlbumDTOList(), mockArtistComplexDTO(), mockTotals());
    }

    public static PlayerStateDTO mockPlayerStateDTO(){
        return new PlayerStateDTO(true, mockTrackDTO(), 0,0,
                0,"off", false);
    }











}
