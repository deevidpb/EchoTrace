package com.onion.spotifystats.security;

import com.onion.spotifystats.SpotifyStatsApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpotifyStatsApplication.class)
@Import(TestOAuth2Config.class)
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void unauthenticatedRequestRedirectsToFrontend() throws Exception {
        mockMvc.perform(get("/api/secure-resource")
                        .header("Origin", "http://localhost:3000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://127.0.0.1:3000?oauth-error=spotify_auth_failed"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));
    }

    @Test
    void logoutReturnsOkForAuthenticatedUser() throws Exception {
        var authentication =
                new UsernamePasswordAuthenticationToken("user", "pass", List.of());

        mockMvc.perform(post("/api/auth/logout")
                        .with(authentication(authentication))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}