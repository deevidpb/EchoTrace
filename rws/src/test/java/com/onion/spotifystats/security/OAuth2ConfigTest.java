package com.onion.spotifystats.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OAuth2ConfigTest {

    @Mock
    private ClientRegistrationRepository clientRegistrationRepository;

    @Mock
    private OAuth2AuthorizedClientService authorizedClientService;

    private final OAuth2Config oAuth2Config = new OAuth2Config();

    @Test
    void testAuthorizedClientManagerCreation() {
        OAuth2AuthorizedClientManager manager = oAuth2Config.authorizedClientManager(
            clientRegistrationRepository,
            authorizedClientService
        );
        
        assertNotNull(manager);
        assertTrue(manager instanceof org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager);
    }

    @Test
    void testAuthorizedClientManagerWithNullRepository() {
        assertThrows(IllegalArgumentException.class, () -> {
            oAuth2Config.authorizedClientManager(
                null,
                authorizedClientService
            );
        });
    }

    @Test
    void testAuthorizedClientManagerWithNullService() {
        assertThrows(IllegalArgumentException.class, () -> {
            oAuth2Config.authorizedClientManager(
                clientRegistrationRepository,
                null
            );
        });
    }

    @Test
    void testAuthorizedClientManagerWithBothNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            oAuth2Config.authorizedClientManager(
                null,
                null
            );
        });
    }

    @Test
    void testAuthorizedClientManagerIsSingleton() {
        OAuth2AuthorizedClientManager manager1 = oAuth2Config.authorizedClientManager(
            clientRegistrationRepository,
            authorizedClientService
        );
        
        OAuth2AuthorizedClientManager manager2 = oAuth2Config.authorizedClientManager(
            clientRegistrationRepository,
            authorizedClientService
        );
        
        assertNotNull(manager1);
        assertNotNull(manager2);
    }
}