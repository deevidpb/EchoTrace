package com.onion.spotifystats.service;

import com.onion.spotifystats.client.UserClient;
import com.onion.spotifystats.dto.response.album.AlbumDTO;
import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.currentuser.*;
import com.onion.spotifystats.dto.response.playlist.PlayListDTO;
import com.onion.spotifystats.dto.response.playlist.UserDTO;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.dto.response.utils.ImageDTO;
import com.onion.spotifystats.model.spotify.response.artist.ArtistComplex;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserFollowedArtistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserPlaylistsResponse;
import com.onion.spotifystats.model.spotify.response.currentuser.CurrentUserSavedTracksResponse;
import com.onion.spotifystats.model.spotify.response.topartists.TopArtistsResponse;
import com.onion.spotifystats.model.spotify.response.toptracks.TopTracksResponse;
import com.onion.spotifystats.model.spotify.response.track.Track;
import com.onion.spotifystats.service.mapper.ArtistMapper;
import com.onion.spotifystats.service.mapper.ImageMapper;
import com.onion.spotifystats.service.mapper.TrackMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public SpotifyUserDTO getMe() {
        CurrentUserResponse user = userClient.getCurrentUser();

        if (user == null) {
            return new SpotifyUserDTO("", "", "", 0, "", List.of());
        }

        return new SpotifyUserDTO(
                user.getId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getFollowers().getTotal(),
                user.getExternalUrls().getSpotify(),
                user.getImages().stream().map(ImageMapper::map).toList()
        );
    }

    public List<ArtistComplexDTO> topArtists( String timeRange, int limit) {
        List<ArtistComplex> current = getArtists(timeRange, limit);

        if (!timeRange.equals("short_term")) {
            return mapArtists(current);
        }

        List<ArtistComplex> prev = getArtists("medium_term", limit);
        return getArtistRanks(mapArtists(current), mapArtists(prev));
    }

    private List<ArtistComplex> getArtists(String timeRange, int limit) {
        TopArtistsResponse response1 = userClient.getTopArtists(timeRange, limit, 0);
        TopArtistsResponse response2 = userClient.getTopArtists(timeRange, limit, limit);
        TopArtistsResponse response3 = userClient.getTopArtists(timeRange, limit, limit * 2);

        if(response1 == null ||  response2 == null || response3 == null) {
            return List.of();
        }

        return Stream.of(
                        response1.getItems(),
                        response2.getItems(),
                        response3.getItems())
                .flatMap(List::stream)
                .toList();
    }

    private List<ArtistComplexDTO> mapArtists(List<ArtistComplex> artists) {
        return artists.stream()
                .map(ArtistMapper::map)
                .toList();
    }

    private List<ArtistComplexDTO> getArtistRanks(List<ArtistComplexDTO> current, List<ArtistComplexDTO> previous) {
        Map<String, Integer> prevRanks = new HashMap<>();
        List<ArtistComplexDTO> result = new ArrayList<>();

        for (int i = 0; i < previous.size(); i++) {
            prevRanks.put(previous.get(i).id(), i +1);
        }

        for (int i = 0; current.size() > i; i++) {
            ArtistComplexDTO artist = current.get(i);
            Integer prevRank = prevRanks.get(artist.id());
            boolean isNew = prevRank == null;
            int rankChange = isNew ? 0 : prevRank - (i + 1);

            result.add(new ArtistComplexDTO(
                    artist.id(),
                    artist.name(),
                    artist.url(),
                    artist.images(),
                    rankChange,
                    isNew
            ));
        }
        return result;
    }

    public ArtistComplexDTO getArtistaRevelacion (List<ArtistComplexDTO> artists){
        ArtistComplexDTO highest = null;

        for (ArtistComplexDTO artist : artists) {
            if (artist.isNew())
                return artist;

            if (artist.rankChange() > 0 &&
                    (highest == null || artist.rankChange() > highest.rankChange()))
                highest = artist;
        }
        return highest;
    }

    public List<TrackDTO> topTracks(String timeRange, int limit) {
        List<Track> current = getTracks(timeRange, limit);

        if (!timeRange.equals("short_term"))
            return mapTracks(current);

        List<Track> prev = getTracks("medium_term", limit);
        return getTrackRanks(mapTracks(current), mapTracks(prev));
    }

    private List<Track> getTracks(String timeRange, int limit) {
        TopTracksResponse response1 = userClient.getTopTracks(timeRange, limit, 0);
        TopTracksResponse response2 = userClient.getTopTracks(timeRange, limit, limit);
        TopTracksResponse response3 = userClient.getTopTracks(timeRange, limit, limit * 2);

        if(response1 == null || response2 == null || response3 == null){
            return List.of();
        }

        return Stream.of(
                        response1.getItems(),
                        response2.getItems(),
                        response3.getItems())
                .flatMap(List::stream)
                .toList();
    }

    private List<TrackDTO> mapTracks(List<Track> tracks) {
        return tracks.stream()
                .map(TrackMapper::map)
                .toList();
    }

    private List<TrackDTO> getTrackRanks(List<TrackDTO> current, List<TrackDTO> previous) {
        Map<String, Integer> prevRanks = new HashMap<>();
        List<TrackDTO> result = new ArrayList<>();

        for (int i = 0; i < previous.size(); i++) {
            prevRanks.put(previous.get(i).id(), i +1);
        }

        for (int i = 0; current.size() > i; i++) {
            TrackDTO track = current.get(i);
            Integer prevRank = prevRanks.get(track.id());
            boolean isNew = prevRank == null;
            int rankChange = isNew ? 0 : prevRank - (i + 1);

            result.add(new TrackDTO(
                    track.id(),
                    track.name(),
                    track.durationMs(),
                    track.album(),
                    track.artists(),
                    track.url(),
                    rankChange,
                    isNew
            ));
        }
        return result;
    }

    public List<AlbumDTO> topAlbums(List<TrackDTO> tracks, int limit) {
        Map<String, Integer> apariciones = new HashMap<>();
        Map<String, AlbumDTO> albumMap = new HashMap<>();

        for (TrackDTO track : tracks) {
            AlbumDTO album = track.album();
            albumMap.put(album.id(), album);

            if(apariciones.containsKey(album.id())) {
                apariciones.put(album.id(), apariciones.get(album.id()) + 1);
            }
            else {
                apariciones.put(album.id(), 1);
            }
        }

        List<Map.Entry<String, Integer>> entryList = apariciones.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList().reversed();

        List<AlbumDTO> result = new ArrayList<>();
        for (int i = 0; i < entryList.size() && i < limit; i++) {
            result.add(albumMap.get(entryList.get(i).getKey()));
        }

        return result;
    }

    public CurrentUserPlaylistsDTO getCurrentUserPlaylists(int limit) {
        CurrentUserPlaylistsResponse response = userClient.getUserPlaylists(limit, 0);

        if (response == null){
            return new CurrentUserPlaylistsDTO(0, List.of());
        }

        return  new CurrentUserPlaylistsDTO(
                response.getTotal(),
                response.getItems().stream().map(item -> new PlayListDTO(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.isCollaborative(),
                        item.isPublic(),
                        item.getExternalUrls().getSpotify(),
                        item.getImages().stream().map(image -> new ImageDTO(
                                image.getUrl(),
                                image.getHeight(),
                                image.getWidth()
                        )).toList(),
                        item.getItems().getTotal(),
                        new UserDTO(
                                item.getOwner().getExternalUrls().getSpotify(),
                                item.getOwner().getDisplayName(),
                                item.getOwner().getId())
                )).toList()
        );
    }

    public CurrentUserSavedTracksDTO getCurrentUserSavedTracks(int limit) {
        CurrentUserSavedTracksResponse response = userClient.getUserSavedTracks(limit, 0);

        if (response == null) {
            return new CurrentUserSavedTracksDTO(0, List.of());
        }

        return new CurrentUserSavedTracksDTO(
                response.getTotal(),
                response.getItems().stream().map(item -> new SavedTracksItemDTO(
                        item.getAddedAt(),
                        TrackMapper.map(item.getTrack())
                )).toList()
        );
    }

    public CurrentUserFollowedArtistsDTO getCurrentUserFollowedArtists(int limit){
        CurrentUserFollowedArtistsResponse response = userClient.getUserFollowedArtists(limit);

        if (response == null){
            return new CurrentUserFollowedArtistsDTO(0, List.of());
        }

        return new CurrentUserFollowedArtistsDTO(
                response.getArtists().getTotal(),
                response.getArtists().getItems().stream().map(ArtistMapper::map).toList()
        );
    }
}
