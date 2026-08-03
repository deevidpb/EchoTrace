package com.onion.spotifystats.controller;

import com.onion.spotifystats.TestData;
import com.onion.spotifystats.controller.web.WebController;
import com.onion.spotifystats.dto.response.stats.StatsSnapshotDTO;
import com.onion.spotifystats.service.SpotifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebController.class)
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpotifyService spotifyService;

    @Test
    void stats() throws Exception {

        StatsSnapshotDTO dto = TestData.mockStatsSnapshotDTO();

        when(spotifyService.stats("short_term", 50, 25))
                .thenReturn(dto);

        mockMvc.perform(get("/api/web/stats")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(spotifyService).stats("short_term", 50, 25);
    }

    @Test
    void statsWithMediumTerm() throws Exception {

        StatsSnapshotDTO dto = TestData.mockStatsSnapshotDTO();

        when(spotifyService.stats("medium_term", 50, 25))
                .thenReturn(dto);

        mockMvc.perform(get("/api/web/stats")
                        .param("timeRange", "medium_term")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(spotifyService).stats("medium_term", 50, 25);
    }

    @Test
    void statsWithLongTerm() throws Exception {

        StatsSnapshotDTO dto = TestData.mockStatsSnapshotDTO();

        when(spotifyService.stats("long_term", 50, 25))
                .thenReturn(dto);

        mockMvc.perform(get("/api/web/stats")
                        .param("timeRange", "long_term")
                        .with(oauth2Login()))
                .andExpect(status().isOk());

        verify(spotifyService).stats("long_term", 50, 25);
    }
}
