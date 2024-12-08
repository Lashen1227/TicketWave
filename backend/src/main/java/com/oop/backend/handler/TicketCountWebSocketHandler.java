package com.oop.backend.handler;

import com.oop.backend.service.TicketPoolService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TicketCountWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, CopyOnWriteArrayList<WebSocketSession>> eventSessions = new ConcurrentHashMap<>();
    private final TicketPoolService ticketPoolService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TicketCountWebSocketHandler(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long eventId = getEventId(session);
        eventSessions.computeIfAbsent(eventId, k -> new CopyOnWriteArrayList<>()).add(session);
        sendCurrentTicketCount(session, eventId);
        startPeriodicUpdates(eventId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long eventId = getEventId(session);
        eventSessions.getOrDefault(eventId, new CopyOnWriteArrayList<>()).remove(session);
    }

    private void sendCurrentTicketCount(WebSocketSession session, Long eventId) {
        if (!session.isOpen()) {
            return;
        }
        int availableTickets = 0;
        var ticketPool = ticketPoolService.getTicketPoolByEventItemId(eventId);
        if (ticketPool != null) {
            availableTickets = ticketPool.getAvailableTickets();
        }
        try {
            session.sendMessage(new TextMessage("Available tickets: " + availableTickets));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPeriodicUpdates(Long eventId) {
        scheduler.scheduleAtFixedRate(() -> {
            for (WebSocketSession session : eventSessions.getOrDefault(eventId, new CopyOnWriteArrayList<>())) {
                sendCurrentTicketCount(session, eventId);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    private Long getEventId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        return Long.parseLong(parts[parts.length - 2]);
    }
}
