package com.oop.backend.cli;

import com.oop.backend.entity.TicketPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TicketPool ticketPool() {
        return new TicketPool();
    }
}
