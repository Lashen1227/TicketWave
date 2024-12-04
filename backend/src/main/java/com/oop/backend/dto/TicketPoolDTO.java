package com.oop.backend.dto;

public class TicketPoolDTO {
    private Long id;
    private int maxPoolSize;
    private int availableTickets;
    private Long eventItemId;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
    public int getAvailableTickets() {
        return availableTickets;
    }
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    public Long getEventItemId() {
        return eventItemId;
    }
    public void setEventItemId(Long eventItemId) {
        this.eventItemId = eventItemId;
    }
}
