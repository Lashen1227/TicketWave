package com.oop.backend.model;

import com.oop.backend.dto.TicketDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private int ticketRetrievalRate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public Customer() {}

    public Customer(String name, String email, String password) {
        super(name, email, password);
    }

    public Customer(String name, String email, int ticketRetrievalRate) {
        super(name, email, true);
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public Customer(String name, String email) {
        super(name, email);
    }

    public int getTicketRetrievalRate() {
        return this.ticketRetrievalRate;
    }

    public List<TicketDTO> getTickets() {
        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getEventItem().getName(),
                        ticket.getTicketId().toString(),
                        ticket.getEventItem().getImageUrl(),
                        ticket.getEventItem().getDateTime(),
                        ticket.getEventItem().getEventId().toString()))
                .collect(Collectors.toList());
    }
}