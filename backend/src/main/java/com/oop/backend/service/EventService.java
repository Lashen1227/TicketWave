package com.oop.backend.service;

import com.oop.backend.model.EventItem;
import com.oop.backend.model.TicketPool;
import com.oop.backend.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.oop.backend.model.Logger.logger;

@Service
public class EventService {
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private EventRepo eventRepository;

    public EventItem getEventById(Long eventItemId) {
        return eventRepository.findById(eventItemId).orElse(null);
    }

    /**
     * Create an event and its corresponding TicketPool
     * @param eventItem
     * @return
     */
    public EventItem createEvent(EventItem eventItem) {
        EventItem savedEventItem = eventRepository.save(eventItem);
        TicketPool ticketPool = new TicketPool(savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool);
        logger.info("TicketPool created for EventItem: " + savedEventItem.getId());
        return eventRepository.save(savedEventItem);
    }

    public List<EventItem> getAllEvents(boolean isSimulated) {
        return eventRepository.findByisSimulated(isSimulated);
    }

    public List<EventItem> getVendorEvents(long vendorId) {
        return eventRepository.findByVendorId(vendorId);
    }
}
