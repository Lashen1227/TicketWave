package com.oop.backend.controller;

import com.oop.backend.config.Configuration;
import com.oop.backend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping
    public ResponseEntity<Configuration> saveConfiguration(@RequestBody Configuration config) {
        Configuration savedConfig = configurationService.saveConfiguration(config);
        return ResponseEntity.ok(savedConfig);
    }

    @GetMapping
    public ResponseEntity<Configuration> loadConfiguration() {
        Optional<Configuration> config = configurationService.loadConfiguration();
        return config.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
