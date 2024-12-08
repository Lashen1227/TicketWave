package com.oop.backend.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TicketWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, CopyOnWriteArrayList<WebSocketSession>> eventSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long eventId = getEventId(session);
        eventSessions.computeIfAbsent(eventId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long eventId = getEventId(session);
        eventSessions.getOrDefault(eventId, new CopyOnWriteArrayList<>()).remove(session);
    }

    public void sendMessageToEvent(Long eventId, String message) {
        for (WebSocketSession session : eventSessions.getOrDefault(eventId, new CopyOnWriteArrayList<>())) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Long getEventId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        // Assuming the event ID is always at the 4th position in the URI
        return Long.parseLong(parts[parts.length - 2]);
    }
}