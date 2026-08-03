package com.onion.spotifystats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onion.spotifystats.TestData;
import com.onion.spotifystats.controller.web.PlayerController;
import com.onion.spotifystats.model.spotify.request.player.*;
import com.onion.spotifystats.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private PlayerService playerService;

    @Test
    void player() throws Exception {

        when(playerService.playerState())
                .thenReturn(TestData.mockPlayerStateDTO());

        mockMvc.perform(get("/api/web/player")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(playerService).playerState();
    }

    @Test
    void play() throws Exception {

        var request = new PlayRequest("spotify:track:123");

        mockMvc.perform(put("/api/web/player/play")
                        .with(oauth2Login())
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(playerService).play(request);
    }

    @Test
    void playWithNullRequest() throws Exception {

        mockMvc.perform(put("/api/web/player/play")
                        .with(oauth2Login())
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isNoContent());

        verify(playerService).play(null);
    }

    @Test
    void pause() throws Exception {

        mockMvc.perform(put("/api/web/player/pause")
                        .with(oauth2Login())
                        .with(csrf()))

                .andExpect(status().isNoContent());

        verify(playerService).pause();
    }

    @Test
    void next() throws Exception {

        mockMvc.perform(post("/api/web/player/next")
                        .with(oauth2Login())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(playerService).next();
    }

    @Test
    void previous() throws Exception {

        mockMvc.perform(post("/api/web/player/previous")
                        .with(oauth2Login())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(playerService).previous();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 75, 100})
    void volume(int volume) throws Exception {

        var request = new VolumeRequest(volume);

        mockMvc.perform(put("/api/web/player/volume")
                        .with(oauth2Login())
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(playerService).volume(volume);
    }
}