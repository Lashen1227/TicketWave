package com.oop.backend.service;

import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.TicketPool;
import com.oop.backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.oop.backend.cli.ConfigCLI.logger;

@Service
public class EventService {
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private EventRepository eventRepository;

    public EventItem getEventById(Long eventItemId) {
        return eventRepository.findById(eventItemId).orElse(null);
    }

    public EventItem createEvent(EventItem eventItem, int maxPoolSize) {
        // Save the EventItem FIRST
        EventItem savedEventItem = eventRepository.save(eventItem);
        // Create and save the TicketPool
        TicketPool ticketPool = new TicketPool(maxPoolSize, savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool); // Assign the saved TicketPool to the EventItem
        logger.info("TicketPool created for EventItem: " + savedEventItem.getId());
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public EventItem createEvent(EventItem eventItem) {
        EventItem savedEventItem = eventRepository.save(eventItem);
        TicketPool ticketPool = new TicketPool(savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool); // Assign the saved TicketPool to the EventItem
        logger.info("TicketPool created for EventItem: " + savedEventItem.getId());
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public List<EventItem> getAllEvents(boolean b) {
        return eventRepository.findByisSimulated(b);
    }

    public List<EventItem> getVendorEvents(long vendorId) {
        return eventRepository.findByVendorId(vendorId);
    }
}
