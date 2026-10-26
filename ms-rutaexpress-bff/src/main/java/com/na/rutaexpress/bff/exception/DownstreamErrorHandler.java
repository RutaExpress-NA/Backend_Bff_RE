package com.na.rutaexpress.bff.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;


@RestControllerAdvice
public class DownstreamErrorHandler {

    
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> onDownstreamError(HttpStatusCodeException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getResponseBodyAsString());
    }

    
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, String>> onUnavailable(ResourceAccessException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "El servicio interno no esta disponible"));
    }
}
