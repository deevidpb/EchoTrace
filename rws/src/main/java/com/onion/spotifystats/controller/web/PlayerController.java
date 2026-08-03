package com.onion.spotifystats.controller.web;

import com.onion.spotifystats.dto.response.currentlyplaying.PlayerStateDTO;
import com.onion.spotifystats.model.spotify.request.player.PlayRequest;
import com.onion.spotifystats.model.spotify.request.player.VolumeRequest;
import com.onion.spotifystats.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/web/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping("")
    public PlayerStateDTO player(){
        return playerService.playerState();
    }

    @PutMapping("/play")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void play (@RequestBody(required = false) PlayRequest request){
        playerService.play(request);
    }

    @PutMapping("/pause")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pause (){
        playerService.pause();
    }

    @PostMapping("/next")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void next(){
        playerService.next();
    }

    @PostMapping("/previous")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void previous(){
        playerService.previous();
    }

    @PutMapping("/volume")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void volume (@RequestBody VolumeRequest request){
        playerService.volume(request.volumePercent());
    }
}
