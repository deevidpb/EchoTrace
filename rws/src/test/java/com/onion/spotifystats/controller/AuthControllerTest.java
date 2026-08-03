package com.onion.spotifystats.controller;

import com.onion.spotifystats.controller.web.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void check() throws Exception {

        mockMvc.perform(get("/api/web/auth/check")
                        .with(oauth2Login()))
                .andExpect(status().isOk());
    }

    @Test
    void checkUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/web/auth/check"))
                .andExpect(status().isUnauthorized());
    }
}
