package com.oop.backend.cli;

import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.Vendor;
import com.oop.backend.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VendorSimulation implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(VendorSimulation.class);
    private final Vendor vendor;
    private final List<EventItem> events;
    private final VendorService vendorService;
    private final boolean[] isSimulating;

    public VendorSimulation(Vendor vendor, List<EventItem> events, VendorService vendorService, boolean[] isSimulating) {
        this.vendor = vendor;
        this.events = events;
        this.vendorService = vendorService;
        this.isSimulating = isSimulating;
    }

    @Override
    public void run() {
        final int releaseRate = vendor.getTicketReleaseRate();
        while (isSimulating[0]) {
            try {
                Thread.sleep(releaseRate * 1000);
                if (!isSimulating[0]) break;
                vendorService.releaseTickets(vendor, events.get((int) (Math.random() * events.size())).getId());
            } catch (InterruptedException e) {
                logger.info("Thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}