package com.onion.spotifystats.controller;

import com.onion.spotifystats.TestData;
import com.onion.spotifystats.service.PlayerService;
import com.onion.spotifystats.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PlayerService playerService;

    @Test
    void me() throws Exception {

        when(userService.getMe())
                .thenReturn(TestData.mockSpotifyUserDTO());

        mockMvc.perform(get("/api/me")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(userService).getMe();
    }

    @Test
    void topArtists() throws Exception {

        when(userService.topArtists("short_term", 20))
                .thenReturn(TestData.mockArtistComplexDTOList());

        mockMvc.perform(get("/api/top-artists")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(userService).topArtists("short_term", 20);
    }

    @Test
    void topArtistsCustomParams() throws Exception {

        when(userService.topArtists("medium_term", 10))
                .thenReturn(TestData.mockArtistComplexDTOList());

        mockMvc.perform(get("/api/top-artists")
                        .param("timeRange", "medium_term")
                        .param("limit", "10")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(userService).topArtists("medium_term", 10);
    }

    @Test
    void topTracks() throws Exception {

        when(userService.topTracks("short_term", 20))
                .thenReturn(TestData.mockTracksDTOList());

        mockMvc.perform(get("/api/top-tracks")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(userService).topTracks("short_term", 20);
    }

    @Test
    void topTracksCustomParams() throws Exception {

        when(userService.topTracks("long_term", 15))
                .thenReturn(TestData.mockTracksDTOList());

        mockMvc.perform(get("/api/top-tracks")
                        .param("timeRange", "long_term")
                        .param("limit", "15")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(userService).topTracks("long_term", 15);
    }

    @Test
    void recently() throws Exception {

        when(playerService.recently(25))
                .thenReturn(TestData.mockRecentlyPlayedItemDTOList());

        mockMvc.perform(get("/api/recently")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(playerService).recently(25);
    }

    @Test
    void recentlyCustomLimit() throws Exception {

        when(playerService.recently(5))
                .thenReturn(TestData.mockRecentlyPlayedItemDTOList());

        mockMvc.perform(get("/api/recently")
                        .param("limit", "5")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(playerService).recently(5);
    }
}