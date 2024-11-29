package com.oop.backend.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poolId;
    private int maxPoolSize;
    private int availableTickets;

    @ManyToOne
    @JoinColumn(name = "event_item_id")
    private EventItem eventItem;

    @OneToMany(mappedBy = "ticketPool", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    // Constructors, getters, and setters
    public TicketPool() {}

    public TicketPool(int maxPoolSize, EventItem eventItem) {
        this.maxPoolSize = maxPoolSize;
        this.eventItem = eventItem;
    }

    public TicketPool(EventItem eventItem) {
        this.maxPoolSize = 0;
        this.eventItem = eventItem;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public Long getPoolId() {
        return poolId;
    }

    public List<Ticket> getTickets() {
        return tickets.stream().filter(Ticket::isAvailable).collect(Collectors.toList());
    }

    public int getAvailableTickets() {
        int availableTickets = (int) tickets.stream().filter(ticket -> ticket.isAvailable()).count();
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getEventName() {
        return eventItem.getName();
    }

    public EventItem getEventItem() {
        return this.eventItem;
    }
}
