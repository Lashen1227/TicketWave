package com.oop.backend.service;

import com.oop.backend.model.Customer;
import com.oop.backend.model.Ticket;
import com.oop.backend.model.TicketPool;
import com.oop.backend.repo.TicketPoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.oop.backend.BackendApplication.*;
import static com.oop.backend.model.Logger.logger;

@Service
public class TicketPoolService {
    /**
     * Lock for thread safety
     * Reference: https://www.geeksforgeeks.org/reentrant-lock-java/
     */
    private final Lock lock = new ReentrantLock();
    @Autowired
    private TicketPoolRepo ticketPoolRepository;
    @Autowired
    private TicketService ticketService;

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    /**
     * Removes a ticket from the ticket pool and assigns it to the customer
     * @param eventItemId the event item id
     * @param customer    the customer
     */
    public void removeTicket(Long eventItemId, Customer customer) {
        lock.lock();
        try {
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId); // Get the ticket pool
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().stream().filter(Ticket::isAvailable).findFirst().orElse(null);
                // If a ticket is available, sell it to the customer
                if (ticket != null) {
                    ticket.sellTicket();
                    ticket.setCustomer(customer);
                    ticketService.saveTicket(ticket);
                    logger.info(Green + customer.getName() + " - Ticket ID " + ticket.getId() + " purchased for " + ticketPool.getEventName() + Reset);
                } else {
                    logger.info(Yellow + "No tickets available for the event: " + ticketPool.getEventName() + Reset);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Add a ticket to the ticket pool
    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
        }
    }
}