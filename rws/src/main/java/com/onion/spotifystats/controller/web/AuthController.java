package com.onion.spotifystats.controller.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/auth")
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<Void> check(
            CsrfToken csrfToken,
            HttpServletResponse response
    ) {
        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        return ResponseEntity.ok().build();
    }

}
