package com.oop.backend.controller;

import com.oop.backend.dto.TicketPoolDTO;
import com.oop.backend.entity.TicketPool;
import com.oop.backend.service.MappingService;
import com.oop.backend.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketpools")
@CrossOrigin(origins = "*")
public class TicketPoolController {
    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("/{eventItemId}")
    public ResponseEntity<TicketPoolDTO> getTicketPoolByEventItemId(@PathVariable Long eventItemId) {
        TicketPool ticketPool = ticketPoolService.getTicketPoolByEventItemId(eventItemId);
        return ticketPool != null ? new ResponseEntity<>(mappingService.mapToTicketPoolDTO(ticketPool), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
