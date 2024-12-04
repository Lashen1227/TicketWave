package com.oop.backend.service;

import com.oop.backend.model.EventItem;
import com.oop.backend.model.Ticket;
import com.oop.backend.model.TicketPool;
import com.oop.backend.model.Vendor;
import com.oop.backend.repo.EventRepo;
import com.oop.backend.repo.TicketPoolRepo;
import com.oop.backend.repo.TicketRepo;
import com.oop.backend.repo.VendorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oop.backend.BackendApplication.*;

@Service
public class VendorService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private VendorRepo vendorRepository;
    @Autowired
    private EventRepo eventRepository;
    @Autowired
    private TicketRepo ticketRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private TicketPoolRepo ticketPoolRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public Vendor createVendor(Vendor vendor) {
        if (userService.emailExists(vendor.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        return vendorRepository.save(vendor);
    }

    @Transactional
    public void releaseTickets(Vendor vendor, Long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        boolean isSimulated = eventItem.isSimulated();
        if (eventItem != null) {
            TicketPool ticketPool = eventItem.getTicketPool();
            // If the ticket pool is not full or if the vendor is simulated, create a new ticket
            if (ticketPool != null) {
                if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize() || !isSimulated) {
                    Ticket ticket = new Ticket(eventItem, isSimulated);
                    ticketRepository.save(ticket);
                    ticketPoolService.addTicket(ticketPool, ticket);
                    ticketPoolRepository.save(ticketPool);
                    logger.info(Magenta + vendor.getName() + " - Released ticket: " + ticket.getId() + " for: " + eventItem.getName() + Reset);
                } else {
                    logger.info(Yellow + vendor.getName() + " - Ticket pool is full for: " + eventItem.getName() + Reset);
                }
            } else {
                logger.info("Ticket pool not found");
            }
        } else {
            logger.info("Event not found");
        }
    }

    public List<Vendor> getAllVendors(boolean isSimulated) {
        return vendorRepository.findByisSimulated(isSimulated);
    }

    public Vendor getVendorById(long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    // Get the vendor of an event
    public Vendor getVendorByEventId(long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        if (eventItem != null) {
            return eventItem.getVendor();
        } else {
            return null;
        }
    }
}