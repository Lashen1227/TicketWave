package com.oop.backend.controller;

import com.oop.backend.dto.EventItemDTO;
import com.oop.backend.model.EventItem;
import com.oop.backend.model.Vendor;
import com.oop.backend.service.EventService;
import com.oop.backend.service.MappingService;
import com.oop.backend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.oop.backend.model.Logger.logger;


@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private MappingService mappingService;
    @Autowired
    private VendorService vendorService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventItemDTO eventItemDTO) {
        try {
            if (eventItemDTO.getEventName() == null || eventItemDTO.getEventLocation() == null || eventItemDTO.getEventDate() == null || eventItemDTO.getEventTime() == null || eventItemDTO.getTicketPrice() == 0 || eventItemDTO.getDetails() == null || eventItemDTO.getImage() == null || eventItemDTO.getVendorId() == null || eventItemDTO.getVendorName() == null) {
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            } else {
                logger.info("Creating event: " + eventItemDTO.getEventName() + " by vendor: " + eventItemDTO.getVendorName() + " with ID: " + eventItemDTO.getVendorId());
                Vendor vendor = vendorService.getVendorById(eventItemDTO.getVendorId());
                EventItem eventItem = new EventItem(eventItemDTO.getEventName(), eventItemDTO.getEventLocation(), eventItemDTO.getEventDate(), eventItemDTO.getEventTime(), eventItemDTO.getTicketPrice(), eventItemDTO.getDetails(), eventItemDTO.getImage(), vendor);
                EventItem createdEvent = eventService.createEvent(eventItem);
                return new ResponseEntity<>(mappingService.mapToEventItemDTO(createdEvent), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventItemDTO>> getAllEvents() {
        List<EventItemDTO> events = eventService.getAllEvents(false)
                .stream()
                .map(mappingService::mapToEventItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{vendorId}/list")
    public ResponseEntity<List<EventItemDTO>> getVendorEvents(@PathVariable long vendorId) {
        List<EventItemDTO> events = eventService.getVendorEvents(vendorId)
                .stream()
                .map(mappingService::mapToEventItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventItemDTO> getEventById(@PathVariable long eventId) {
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mappingService.mapToEventItemDTO(eventItem), HttpStatus.OK);
    }
}