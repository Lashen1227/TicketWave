package com.oop.backend.service;

import com.oop.backend.config.Configuration;
import com.oop.backend.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration saveConfiguration(Configuration config) {
        return configurationRepository.save(config);
    }

    public Optional<Configuration> loadConfiguration() {
        return configurationRepository.findById(1L);
    }
}
