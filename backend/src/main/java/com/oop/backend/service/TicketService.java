package com.oop.backend.service;

import com.oop.backend.model.Ticket;
import com.oop.backend.repo.TicketRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    private TicketRepo ticketRepository;

    /**
     * Save a ticket to the database
     * @param ticket the ticket to save
     * @return the saved ticket
     */
    public Ticket saveTicket(Ticket ticket) {
        try {
            return ticketRepository.save(ticket);
        } catch (OptimisticLockingFailureException e) {
            // When the version of the entity in the database does not match the version in the entity
            throw new RuntimeException("Ticket update failed due to concurrent modification", e);
        }
    }

    public List<Ticket> getTicketsByEventId(Long eventItemId) {
        return ticketRepository.findByEventItemId(eventItemId);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
