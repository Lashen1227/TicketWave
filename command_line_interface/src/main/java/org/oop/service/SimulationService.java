package org.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationService {
    // AtomicBoolean to check if simulation is running and Available tickets count
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicInteger availableTickets = new AtomicInteger(0);
    // List to store all threads
    private final List<Thread> threads = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(SimulationService.class.getName());
    public static final String Reset = "\u001B[0m";
    public static final String Red = "\u001B[31m";
    public static final String Green = "\u001B[32m";
    public static final String Yellow = "\u001B[33m";
    public static final String Magenta = "\u001B[35m";

    public void startSimulation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vendors: ");
        int vendorCount = scanner.nextInt();
        System.out.print("Enter the number of customers: ");
        int customerCount = scanner.nextInt();

        if (vendorCount <= 0 || customerCount <= 0) {
            logger.info(Red  +"Vendors and customers count must be greater than 0. Please try again."  + Reset);
            return;
        }
        if (running.get()) {
            logger.info(Red + "Simulation already running. Please stop the current simulation first." + Reset);
            return;
        }
        running.set(true);
        threads.clear();
        System.out.println("Simulation started. Press Enter to stop the simulation.");

        // Thread to listen for Enter key to stop simulation
        Thread stopperThread = new Thread(() -> {
            try (Scanner stopScanner = new Scanner(System.in)) {
                stopScanner.nextLine();
                stopSimulation();
            }
        });
        threads.add(stopperThread);
        stopperThread.start();

        // Create and start vendor threads based on user input
        for (int i = 1; i <= vendorCount; i++) {
            final int vendorId = i;
            Thread vendorThread = new Thread(() -> {
                while (running.get()) {
                    try {
                        addTickets(1, vendorId);
                        Thread.sleep((long) (Math.random() * 2000 + 1000)); // Random delay between 1-3 seconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            vendorThread.setName("Vendor-" + vendorId);
            threads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads based on user input
        for (int i = 1; i <= customerCount; i++) {
            final int customerId = i;
            Thread customerThread = new Thread(() -> {
                while (running.get()) {
                    try {
                        buyTickets(1, customerId);
                        Thread.sleep((long) (Math.random() * 2000 + 1000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            customerThread.setName("Customer-" + customerId);
            threads.add(customerThread);
            customerThread.start();
        }
    }

    public synchronized void stopSimulation() {
        if (!running.get()) {
            System.out.println(Red + "Simulation is not running." + Reset);
            return;
        }
        running.set(false);
        System.out.println("Stopping simulation...");
        threads.forEach(Thread::interrupt); // Interrupt all threads
        threads.clear();
        System.out.println("Simulation stopped.");
    }

    /**
     * Adds tickets to the available tickets count.
     * @param count    the number of tickets to add
     * @param vendorId the ID of the vendor adding tickets
     */
    private synchronized void addTickets(int count, int vendorId) {
        availableTickets.addAndGet(count);
        logger.info(Magenta + "[Thread " + Thread.currentThread().getId() + "]  Vendor-" + vendorId
                + " added " + count + " ticket. Available tickets: " + availableTickets.get() + Reset);
    }

    /**
     * Buys tickets from the available tickets count.
     * @param count       the number of tickets to buy
     * @param customerId  the ID of the customer buying tickets
     */
    private synchronized void buyTickets(int count, int customerId) {
        if (availableTickets.get() >= count) {
            availableTickets.addAndGet(-count);
            logger.info(Green + "[Thread " + Thread.currentThread().getId() + "]  Customer-" + customerId
                    + " bought " + count + " ticket. Remaining tickets: " + availableTickets.get() + Reset);
        } else {
            logger.info(Yellow + "[Thread " + Thread.currentThread().getId() + "]  Customer-" + customerId
                    + " tried to buy " + count + " ticket(s), but not enough tickets are available." + Reset);
        }
    }
}
