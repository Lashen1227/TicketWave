package com.oop.backend.controller;

import com.oop.backend.dto.TicketDTO;
import com.oop.backend.entity.Ticket;
import com.oop.backend.service.MappingService;
import com.oop.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByEventId(@PathVariable Long eventId) {
        List<TicketDTO> tickets = ticketService.getTicketsByEventId(eventId)
                .stream()
                .map(mappingService::mapToTicketDTO)
                .collect(Collectors.toList());
        return tickets != null ? new ResponseEntity<>(tickets, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.getAllTickets()
                .stream()
                .map(mappingService::mapToTicketDTO)
                .collect(Collectors.toList());
        return tickets != null ? new ResponseEntity<>(tickets, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}