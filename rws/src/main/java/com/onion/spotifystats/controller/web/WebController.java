package com.onion.spotifystats.controller.web;

import com.onion.spotifystats.dto.response.stats.StatsSnapshotDTO;
import com.onion.spotifystats.service.SpotifyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/web")
public class WebController {

    private final SpotifyService spotifyService;
    private static final int TOP_LIMIT = 50;
    private static final int RECENT_LIMIT = 25;

    public WebController(SpotifyService spotifyService){
        this.spotifyService = spotifyService;
    }

    @GetMapping("/stats")
    public StatsSnapshotDTO stats(@RequestParam(defaultValue = "short_term") String timeRange){
        return spotifyService.stats(timeRange, TOP_LIMIT, RECENT_LIMIT);
    }


}
