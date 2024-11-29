package com.oop.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private int ticketRetrievalRate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    // Constructors, getters, and setters
    public Customer() {
    }

    public Customer(String name, String email, String password) {
        super(name, email, password);
    }

    public Customer(String name, int ticketRetrievalRate) {
        super(name, true);
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public Customer(String name, String email) {
        super(name, email);
    }

    public int getTicketRetrievalRate() {
        return this.ticketRetrievalRate;
    }

    // ... getters and setters ...
}
