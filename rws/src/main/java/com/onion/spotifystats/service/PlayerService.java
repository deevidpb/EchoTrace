package com.onion.spotifystats.service;

import com.onion.spotifystats.client.PlayerClient;
import com.onion.spotifystats.dto.request.player.PlayRequestDTO;
import com.onion.spotifystats.dto.response.currentlyplaying.PlayerStateDTO;
import com.onion.spotifystats.dto.response.recentlyplayed.RecentlyPlayedItemDTO;
import com.onion.spotifystats.model.spotify.request.player.PlayRequest;
import com.onion.spotifystats.model.spotify.response.currentlyplaying.PlayerState;
import com.onion.spotifystats.model.spotify.response.recentlyplayed.RecentlyPlayedResponse;
import com.onion.spotifystats.service.mapper.TrackMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerClient playerClient;

    public PlayerService(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    public PlayerStateDTO playerState (){
        PlayerState response = playerClient.currentlyPlaying();

        if (response == null){
            return new PlayerStateDTO(false, null, 0, 0,
                    0, "off", false);
        }


        return new PlayerStateDTO(
                response.isPlaying(),
                TrackMapper.map(response.getItem()),
                response.getProgressMs(),
                response.getItem().getDurationMs(),
                response.getDevice().getVolumePercent(),
                response.getRepeatState(),
                response.isShuffleState()
        );
    }

    public List<RecentlyPlayedItemDTO> recently(int limit) {
        RecentlyPlayedResponse response =  playerClient.recently(limit);
        return response.getItems().stream()
                .map(item -> new RecentlyPlayedItemDTO(
                        TrackMapper.map(item.getTrack()),
                        item.getPlayedAt()
                ))
                .toList();
    }

    public void play (PlayRequest request){
        PlayRequestDTO dto = null;
        if (request != null){
            dto = new PlayRequestDTO(
                    null, List.of(request.uri()), null);
        }
        playerClient.play(dto);
    }

    public void pause (){playerClient.pause();}

    public void next (){playerClient.next();}

    public void previous(){playerClient.previous();}

    public void volume(int percent){playerClient.volume(percent);}
}
