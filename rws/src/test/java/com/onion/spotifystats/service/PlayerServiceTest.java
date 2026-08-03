package com.onion.spotifystats.service;

import com.onion.spotifystats.TestData;
import com.onion.spotifystats.client.PlayerClient;
import com.onion.spotifystats.dto.request.player.PlayRequestDTO;
import com.onion.spotifystats.dto.response.currentlyplaying.PlayerStateDTO;
import com.onion.spotifystats.dto.response.recentlyplayed.RecentlyPlayedItemDTO;
import com.onion.spotifystats.model.spotify.request.player.PlayRequest;
import com.onion.spotifystats.service.mapper.TrackMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerClient playerClient;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayerStateNull(){
        when(playerClient.currentlyPlaying()).thenReturn(null);
        PlayerStateDTO result = playerService.playerState();

        assertNotNull(result);
        assertFalse(result.isPlaying());
        assertNull(result.track());
        assertEquals(0, result.progressMs());
        assertEquals(0, result.durationMs());
        assertEquals(0, result.volume());
        assertEquals("off", result.repeatMode());
        assertFalse(result.shuffleState());

        verify(playerClient).currentlyPlaying();
    }

    @Test
    void getPlayerState(){
        when(playerClient.currentlyPlaying()).thenReturn(TestData.mockPlayerState());

        PlayerStateDTO dto = playerService.playerState();
        assertNotNull(dto);
        assertTrue(dto.isPlaying());
        assertEquals(TrackMapper.map(TestData.mockTrack()), dto.track());
        assertEquals(0, dto.progressMs());
        assertEquals(0, dto.durationMs());
        assertEquals(0, dto.volume());
        assertEquals("off", dto.repeatMode());
        assertFalse(dto.shuffleState());

    }

    @Test
    void recently(){
        when(playerClient.recently(1)).thenReturn(TestData.mockRecentlyPlayedResponse());

        List<RecentlyPlayedItemDTO> dto = playerService.recently(1);

        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(TrackMapper.map(TestData.mockTrack()), dto.getFirst().track());
        assertEquals("played-at", dto.getFirst().playedAt());
    }

    @Test
    void playNull(){
        playerService.play(null);
        verify(playerClient).play(null);
    }

    @Test
    void play(){
        PlayRequest request = new PlayRequest("uri");
        playerService.play(request);

        PlayRequestDTO dto = new PlayRequestDTO(
                null, List.of(request.uri()), null);

        ArgumentCaptor<PlayRequestDTO> captor =
                ArgumentCaptor.forClass(PlayRequestDTO.class);

        verify(playerClient).play(captor.capture());
        assertEquals(dto, captor.getValue());
    }

    @Test
    void pause(){
        playerService.pause();
        verify(playerClient).pause();
    }

    @Test
    void next(){
        playerService.next();
        verify(playerClient).next();
    }

    @Test
    void previous(){
        playerService.previous();
        verify(playerClient).previous();
    }

    @Test
    void volume(){
        playerService.volume(0);
        verify(playerClient).volume(0);
    }
}
