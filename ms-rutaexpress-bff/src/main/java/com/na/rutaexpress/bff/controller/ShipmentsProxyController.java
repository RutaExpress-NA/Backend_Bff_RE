package com.na.rutaexpress.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentsProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rutaexpress.services.shipments-url}")
    private String shipmentsUrl;

    @GetMapping
    public ResponseEntity<String> listar() {
        return restTemplate.getForEntity(shipmentsUrl + "/api/shipments", String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id) {
        return restTemplate.getForEntity(shipmentsUrl + "/api/shipments/" + id, String.class);
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody String body) {
        HttpEntity<String> request = new HttpEntity<>(body);
        return restTemplate.postForEntity(shipmentsUrl + "/api/shipments", request, String.class);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestBody String body) {
        HttpEntity<String> request = new HttpEntity<>(body);
        return restTemplate.exchange(
            shipmentsUrl + "/api/shipments/" + id + "/status",
            HttpMethod.PUT, request, String.class
        );
    }
}