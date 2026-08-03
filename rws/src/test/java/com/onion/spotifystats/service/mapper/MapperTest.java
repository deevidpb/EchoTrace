package com.onion.spotifystats.service.mapper;

import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.artist.ArtistDTO;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.model.spotify.response.album.Album;
import com.onion.spotifystats.model.spotify.response.artist.Artist;
import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import com.onion.spotifystats.model.spotify.response.track.Track;
import com.onion.spotifystats.model.spotify.response.utils.ExternalUrls;
import com.onion.spotifystats.model.spotify.response.utils.Image;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void mapAlbum(){
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


        AlbumDTO dto = AlbumMapper.map(album);
        assertNotNull(dto);
        assertEquals("album-id", dto.id());
        assertEquals("album-name", dto.name());
        assertEquals(0, dto.totalTracks());
        assertEquals("spotify-url", dto.url());

        assertEquals(1, dto.images().size());
        assertNotNull(dto.images().getFirst());
        assertEquals("image-url", dto.images().getFirst().url());
        assertEquals(100, dto.images().getFirst().width());
        assertEquals(100, dto.images().getFirst().height());

        assertEquals(2, dto.artists().size());

        assertNotNull(dto.artists().getFirst());
        assertEquals("artist1-id", dto.artists().getFirst().id());
        assertEquals("spotify-artist-1", dto.artists().getFirst().name());
        assertEquals("spotify-url-artist-1", dto.artists().getFirst().spotifyUrl());
        assertNotNull(dto.artists().get(1));
        assertEquals("artist2-id", dto.artists().get(1).id());
        assertEquals("spotify-artist-2", dto.artists().get(1).name());
        assertEquals("spotify-url-artist-2", dto.artists().get(1).spotifyUrl());
    }

    @Test
    void mapArtist() {
        Artist artist = new Artist();
        artist.setId("id");
        artist.setName("name");
        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify-url");
        artist.setExternalUrls(externalUrls);

        ArtistDTO dto = ArtistMapper.map(artist);
        assertNotNull(dto);
        assertEquals("id", dto.id());
        assertEquals("name", dto.name());
        assertEquals("spotify-url", dto.spotifyUrl());


    }

    @Test
    void mapArtistComplex() {
        ArtistComplex artist = new ArtistComplex();
        artist.setId("id");
        artist.setName("name");
        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setSpotify("spotify-url");
        artist.setExternalUrls(externalUrls);
        Image img = new Image();
        img.setHeight(100);
        img.setWidth(100);
        img.setUrl("image-url");

        List<Image> images = new ArrayList<>();
        images.add(img);
        artist.setImages(images);

        ArtistComplexDTO dto = ArtistMapper.map(artist);
        assertNotNull(dto);
        assertEquals("id", dto.id());
        assertEquals("name", dto.name());
        assertEquals("spotify-url", dto.url());

        assertEquals(1, dto.images().size());
        assertNotNull(dto.images().getFirst());
        assertEquals("image-url", dto.images().getFirst().url());
        assertEquals(100, dto.images().getFirst().width());
        assertEquals(100, dto.images().getFirst().height());

        assertEquals(0, dto.rankChange());
        assertFalse(dto.isNew());

    }

    @Test
    void mapTrack() {
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

        TrackDTO dto = TrackMapper.map(track);

        assertNotNull(dto);
        assertEquals("track-id", dto.id());
        assertEquals("track-name", dto.name());
        assertEquals(0, dto.durationMs());
        assertNotNull(dto.album());

        assertEquals(2, dto.artists().size());

        assertNotNull(dto.artists().getFirst());
        assertEquals("artist3-id", dto.artists().getFirst().id());
        assertEquals("spotify-artist-3", dto.artists().getFirst().name());
        assertEquals("spotify-url-artist-3", dto.artists().getFirst().spotifyUrl());
        assertNotNull(dto.artists().get(1));
        assertEquals("artist4-id", dto.artists().get(1).id());
        assertEquals("spotify-artist-4", dto.artists().get(1).name());
        assertEquals("spotify-url-artist-4", dto.artists().get(1).spotifyUrl());

        assertEquals("spotify-url", dto.url());
        assertEquals(0, dto.rankChange());
        assertFalse(dto.isNew());
    }
}
