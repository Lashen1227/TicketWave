package com.oop.backend.controller;

import com.oop.backend.dto.VendorDTO;
import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.Vendor;
import com.oop.backend.service.MappingService;
import com.oop.backend.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendors")
@CrossOrigin(origins = "*")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MappingService mappingService;

    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        Vendor vendor = new Vendor(vendorDTO.getName(), vendorDTO.getEmail(), vendorDTO.getPassword());
        return new ResponseEntity<>(mappingService.mapToVendorDTO(vendorService.createVendor(vendor)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors(true)
                .stream()
                .map(mappingService::mapToVendorDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/release")
    public ResponseEntity<String> releaseTickets(@PathVariable long eventId, @RequestParam int ticketCount) {
        try {
            Vendor vendor = vendorService.getVendorByEventId(eventId);
            if (vendor == null) {
                return new ResponseEntity<>("Vendor not found", HttpStatus.NOT_FOUND);
            } else if (vendor.isSimulated()) {
                return new ResponseEntity<>("Vendor is simulated", HttpStatus.BAD_REQUEST);
            }
            for (int i = 0; i < ticketCount; i++) {
                vendorService.releaseTickets(vendor, eventId);
            }
            return new ResponseEntity<>("Tickets released", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error releasing tickets", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}