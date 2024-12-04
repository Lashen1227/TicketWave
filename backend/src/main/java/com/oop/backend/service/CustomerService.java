package com.oop.backend.service;

import com.oop.backend.model.Customer;
import com.oop.backend.model.EventItem;
import com.oop.backend.repo.CustomerRepo;
import com.oop.backend.repo.EventRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final Lock lock = new ReentrantLock();

    @Autowired
    private CustomerRepo customerRepository;
    @Autowired
    private EventRepo eventRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private UserService userService;

    /**
     * Create a new customer
     * @param customer Customer object
     * @return Customer object
     */
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (userService.emailExists(customer.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    /**
     * Purchase a ticket for a customer
     * @param customer Customer object
     * @param eventItemId EventItem ID
     */
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
