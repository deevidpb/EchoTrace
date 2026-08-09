package com.onion.spotifystats.controller.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/auth")
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<Boolean> check(
            Authentication authentication,
            CsrfToken csrfToken,
            HttpServletResponse response
    ) {
        if (csrfToken != null && response != null) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }

        // Comprobamos si hay un usuario autenticado y no es anónimo
        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        return ResponseEntity.ok(isAuthenticated);
    }

}
