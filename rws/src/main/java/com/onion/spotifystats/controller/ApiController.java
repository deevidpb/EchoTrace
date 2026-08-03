package com.onion.spotifystats.controller;

import com.onion.spotifystats.dto.response.artist.ArtistComplexDTO;
import com.onion.spotifystats.dto.response.currentuser.SpotifyUserDTO;
import com.onion.spotifystats.dto.response.recentlyplayed.RecentlyPlayedItemDTO;
import com.onion.spotifystats.dto.response.track.TrackDTO;
import com.onion.spotifystats.service.PlayerService;
import com.onion.spotifystats.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final PlayerService playerService;


    public ApiController(UserService userService, PlayerService playerService) {
        this.userService = userService;
        this.playerService = playerService;
    }

    @GetMapping("/me")
    public SpotifyUserDTO me() {
        return userService.getMe();
    }

    @GetMapping("/top-artists")
    public List<ArtistComplexDTO> topArtists(@RequestParam(defaultValue = "short_term") String timeRange,
                                      @RequestParam(defaultValue = "20") int limit){
        return userService.topArtists(timeRange, limit);
    }

    @GetMapping("/top-tracks")
    public List<TrackDTO> topTracks(@RequestParam(defaultValue = "short_term") String timeRange,
                                    @RequestParam(defaultValue = "20") int limit){
        return userService.topTracks(timeRange, limit);
    }

    @GetMapping("/recently")
    public List<RecentlyPlayedItemDTO> recently(@RequestParam(defaultValue = "25") int limit){
        return playerService.recently(limit);
    }
}
