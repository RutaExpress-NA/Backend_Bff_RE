package com.na.rutaexpress.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    @GetMapping
    public ResponseEntity<String> listar() {
        return asJson(restTemplate.getForEntity(shipmentsUrl + "/api/shipments", String.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id) {
        return asJson(restTemplate.getForEntity(shipmentsUrl + "/api/shipments/" + id, String.class));
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return asJson(restTemplate.postForEntity(shipmentsUrl + "/api/shipments", request, String.class));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestBody String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return asJson(restTemplate.exchange(
            shipmentsUrl + "/api/shipments/" + id + "/status",
            HttpMethod.PUT, request, String.class
        ));
    }
}