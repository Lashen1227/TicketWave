package com.oop.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("VENDOR")
public class Vendor extends User {
    private int ticketReleaseRate;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventItem> events;

    // Constructors, getters, and setters
    public Vendor() {}

    public Vendor(String name, String email, String password) {
        super(name, email, password);
    }

    public Vendor(String name, int ticketReleaseRate) {
        super(name, true); // Assume simulated vendors
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    // ... getters and setters ...
}
