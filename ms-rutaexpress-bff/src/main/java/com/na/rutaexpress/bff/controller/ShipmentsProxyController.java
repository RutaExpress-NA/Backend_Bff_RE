package com.na.rutaexpress.bff.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentsProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rutaexpress.services.shipments-url}")
    private String shipmentsUrl;

    private ResponseEntity<String> asJson(ResponseEntity<String> resp) {
        return ResponseEntity.status(resp.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(resp.getBody());
    }

    private HttpEntity<Map<String, Object>> jsonEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

   
    private String userIdOf(Jwt jwt) {
        if (jwt == null) {
            return "desconocido";   
        }
        String user = jwt.getClaimAsString("preferred_username");
        return user != null ? user : jwt.getSubject();
    }

    @GetMapping
    public ResponseEntity<String> listar() {
        return asJson(restTemplate.getForEntity(shipmentsUrl + "/api/shipments", String.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id) {
        return asJson(restTemplate.getForEntity(shipmentsUrl + "/api/shipments/" + id, String.class));
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body,
                                        @AuthenticationPrincipal Jwt jwt) {
      
        body.putIfAbsent("createdByUserId", userIdOf(jwt));
        return asJson(restTemplate.postForEntity(
            shipmentsUrl + "/api/shipments", jsonEntity(body), String.class));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id,
                                                @RequestBody Map<String, Object> body,
                                                @AuthenticationPrincipal Jwt jwt) {
  
        if (!body.containsKey("nuevoStatus") && body.containsKey("status")) {
            body.put("nuevoStatus", body.remove("status"));
        }
   
        body.putIfAbsent("byUserId", userIdOf(jwt));
        return asJson(restTemplate.exchange(
            shipmentsUrl + "/api/shipments/" + id + "/status",
            HttpMethod.PUT, jsonEntity(body), String.class
        ));
    }
}
