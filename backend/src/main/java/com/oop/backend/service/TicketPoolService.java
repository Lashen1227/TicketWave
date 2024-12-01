package com.oop.backend.service;

import com.oop.backend.entity.Customer;
import com.oop.backend.entity.Ticket;
import com.oop.backend.entity.TicketPool;
import com.oop.backend.repository.TicketPoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.oop.backend.BackendApplication.*;
import static com.oop.backend.cli.ConfigCLI.logger;

@Service
public class TicketPoolService {
    private final Lock lock = new ReentrantLock();
    @Autowired
    private TicketPoolRepository ticketPoolRepository;
    @Autowired
    private TicketService ticketService;

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    public void removeTicket(Long eventItemId, Customer customer) {
        lock.lock();
        try {
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().stream().filter(Ticket::isAvailable).findFirst().orElse(null);
                if (ticket != null) {
                    ticket.sellTicket();
                    ticket.setCustomer(customer);
                    ticketService.saveTicket(ticket);
                    logger.info(Green + customer.getName() + " - Ticket " + ticket.getId() + " purchased for " + ticketPool.getEventName() + Reset);
                } else {
                    logger.info(Yellow + "No tickets available for the event: " + ticketPool.getEventName() + Reset);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
        }
    }
}