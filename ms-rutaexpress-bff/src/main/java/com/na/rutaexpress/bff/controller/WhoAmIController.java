package com.na.rutaexpress.bff.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhoAmIController {

    @GetMapping("/api/whoami")
    public Map<String, Object> whoAmI(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return Map.of("mensaje", "Perfil dev activo: no se valido ningun token.");
        }
        return Map.of(
            "subject", jwt.getSubject(),
            "issuer", String.valueOf(jwt.getIssuer()),
            "audience", jwt.getAudience(),
            "roles", jwt.getClaimAsStringList("roles") != null
                ? jwt.getClaimAsStringList("roles")
                : List.of("(sin claim 'roles')")
        );
    }
}