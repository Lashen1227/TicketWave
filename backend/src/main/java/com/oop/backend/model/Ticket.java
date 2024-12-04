package com.oop.backend.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @ManyToOne
    private TicketPool ticketPool;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private boolean isSold = false;
    private boolean isSimulated;

    @ManyToOne
    private EventItem eventItem; // Link to Event
    @ManyToOne
    private Customer customer; // Link to Customer

    // Constructors, getters, and setters
    public Ticket() {
    }

    public Ticket(EventItem eventItem) {
        this.eventItem = eventItem;
        this.isSimulated = false;
        this.ticketPool = eventItem.getTicketPool();
    }

    public Ticket(EventItem eventItem, boolean isSimulated) {
        this.eventItem = eventItem;
        this.isSimulated = isSimulated;
        this.ticketPool = eventItem.getTicketPool();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return this.id;
    }

    public double getTicketPrice() {
        return eventItem.getTicketPrice();
    }

    public boolean isAvailable() {
        return !isSold;
    }

    public void sellTicket() {
        isSold = true;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public EventItem getEventItem() {
        return this.eventItem;
    }

    public Object getTicketId() {
        return this.id;
    }

    // ... getters and setters ...
}