package com.oop.backend.service;

import com.oop.backend.entity.Customer;
import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.Ticket;
import com.oop.backend.entity.TicketPool;
import com.oop.backend.repository.CustomerRepository;
import com.oop.backend.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.oop.backend.BackendApplication.*;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final Lock lock = new ReentrantLock();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private UserService userService;

    @Transactional
    public Customer createCustomer(Customer customer) {
        if (userService.emailExists(customer.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public void purchaseTicket(Customer customer, long eventItemId) {
        lock.lock();
        try {
            EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
            if (eventItem != null && eventItem.getTicketPool() != null) {
                ticketPoolService.removeTicket(eventItemId, customer);
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Customer> getAllCustomers(boolean isSimulated) {
        return customerRepository.findByisSimulated(isSimulated);
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

}
