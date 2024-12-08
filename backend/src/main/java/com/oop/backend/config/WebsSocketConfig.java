package com.oop.backend.config;

import com.oop.backend.handler.TicketCountWebSocketHandler;
import com.oop.backend.handler.TicketWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsSocketConfig implements WebSocketConfigurer {

    private final TicketWebSocketHandler ticketWebSocketHandler;
    private final TicketCountWebSocketHandler ticketCountWebSocketHandler;

    public WebsSocketConfig(TicketWebSocketHandler ticketWebSocketHandler, TicketCountWebSocketHandler ticketCountWebSocketHandler) {
        this.ticketWebSocketHandler = ticketWebSocketHandler;
        this.ticketCountWebSocketHandler = ticketCountWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ticketWebSocketHandler, "/ws/event/{eventId}/purchases")
                .setAllowedOrigins("*");
        registry.addHandler(ticketCountWebSocketHandler, "/ws/event/{eventId}/tickets")
                .setAllowedOrigins("*");
    }
}
