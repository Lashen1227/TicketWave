package com.oop.backend.service;

import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.Ticket;
import com.oop.backend.entity.TicketPool;
import com.oop.backend.entity.Vendor;
import com.oop.backend.repository.EventRepository;
import com.oop.backend.repository.TicketPoolRepository;
import com.oop.backend.repository.TicketRepository;
import com.oop.backend.repository.VendorRepository;
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
    private VendorRepository vendorRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private TicketPoolRepository ticketPoolRepository;
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

    public Vendor getVendorByEventId(long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        if (eventItem != null) {
            return eventItem.getVendor();
        } else {
            return null;
        }
    }
}