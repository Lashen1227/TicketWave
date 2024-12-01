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

    public Configuration getConfiguration() {
        Optional<Configuration> configuration = configurationRepository.findById(1L);
        return configuration.orElse(null);
    }

    public Configuration saveConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    public Optional<Configuration> loadConfiguration() {
        return configurationRepository.findById(1L);
    }
}
