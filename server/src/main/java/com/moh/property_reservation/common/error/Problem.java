package com.moh.property_reservation.common.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public record Problem(
        String title,
        int status,
        List<Error> errors
) {
    public ResponseEntity<Problem> toResponse() {
        return ResponseEntity
                .status(status)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(this);
    }
}
