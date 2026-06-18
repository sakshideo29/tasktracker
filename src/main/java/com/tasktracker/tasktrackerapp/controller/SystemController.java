package com.tasktracker.tasktrackerapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SystemController {

    private final DataSource dataSource;

    @GetMapping("/healthz")
    public ResponseEntity<Map<String, String>> health() {

        log.info("Health check requested");

        return ResponseEntity.ok(
                Map.of("status", "ok")
        );
    }

    @GetMapping("/readyz")
    public ResponseEntity<Map<String, String>> ready() {

        log.info("Readiness check requested");

        try (Connection connection = dataSource.getConnection()) {

            if (connection.isValid(2)) {

                log.info("Database connection successful");

                return ResponseEntity.ok(
                        Map.of("status", "ready")
                );
            }

        } catch (Exception e) {

            log.error("Database connection failed", e);
        }

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status", "not ready"));
    }
}