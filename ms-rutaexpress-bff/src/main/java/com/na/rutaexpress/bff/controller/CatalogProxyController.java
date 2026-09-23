package com.na.rutaexpress.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/catalog")
public class CatalogProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rutaexpress.services.catalog-url}")
    private String catalogUrl;

    @GetMapping("/services")
    public ResponseEntity<String> listarServicios() {
        return restTemplate.getForEntity(catalogUrl + "/api/catalog/services", String.class);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<String> obtenerServicio(@PathVariable Long id) {
        return restTemplate.getForEntity(catalogUrl + "/api/catalog/services/" + id, String.class);
    }

    @PostMapping("/services")
    public ResponseEntity<String> crearServicio(@RequestBody String body) {
        HttpEntity<String> request = new HttpEntity<>(body);
        return restTemplate.postForEntity(catalogUrl + "/api/catalog/services", request, String.class);
    }

    @GetMapping("/fleet-capacity")
    public ResponseEntity<String> listarFlota() {
        return restTemplate.getForEntity(catalogUrl + "/api/catalog/fleet-capacity", String.class);
    }
}