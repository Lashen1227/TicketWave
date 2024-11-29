package com.oop.backend.cli;

import com.oop.backend.entity.Customer;
import com.oop.backend.entity.EventItem;
import com.oop.backend.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomerSimulation implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSimulation.class);
    private final Customer customer;
    private final List<EventItem> events;
    private final CustomerService customerService;
    private final boolean[] isSimulating;

    public CustomerSimulation(Customer customer, List<EventItem> events, CustomerService customerService, boolean[] isSimulating) {
        this.customer = customer;
        this.events = events;
        this.customerService = customerService;
        this.isSimulating = isSimulating;
    }

    @Override
    public void run() {
        final int retrievalRate = customer.getTicketRetrievalRate();
        while (isSimulating[0]) {
            try {
                customerService.purchaseTicket(customer, events.get((int) (Math.random() * events.size())).getId());
                Thread.sleep(retrievalRate * 1000);
                if (!isSimulating[0]) break;
            } catch (InterruptedException e) {
                logger.info("Thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}