package com.oop.backend.controller;

import com.oop.backend.dto.CustomerDTO;
import com.oop.backend.dto.TicketDTO;
import com.oop.backend.entity.Customer;
import com.oop.backend.service.CustomerService;
import com.oop.backend.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MappingService mappingService;

    // Create a new customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getPassword());
            return new ResponseEntity<>(mappingService.mapToCustomerDTO(customerService.createCustomer(customer)), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    // Buy a ticket
    @GetMapping("/{customerId}/buy/{eventItemId}")
    public ResponseEntity<String> purchaseTicket(@PathVariable long customerId, @PathVariable long eventItemId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            } else if (customer.isSimulated()) {
                return new ResponseEntity<>("Customer is simulated", HttpStatus.BAD_REQUEST);
            }
            customerService.purchaseTicket(customer, eventItemId);
            return new ResponseEntity<>("Ticket purchased", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error purchasing ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all purchased tickets
    @GetMapping("/{customerId}/tickets")
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customer.getTickets(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}