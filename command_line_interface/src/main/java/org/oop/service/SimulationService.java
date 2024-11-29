package org.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationService {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicInteger availableTickets = new AtomicInteger(0);
    private final List<Thread> threads = new ArrayList<>();

    /**
     * Configures simulation parameters.
     * @param scanner Input scanner for user input.
     */
    public void configureSimulation(Scanner scanner) {
        int vendors = getIntInput(scanner, "Enter Number of Vendors: ");
        int customers = getIntInput(scanner, "Enter Number of Customers: ");
        int maxTickets = getIntInput(scanner, "Enter Maximum Tickets for the Simulation: ");

        availableTickets.set(maxTickets);
        System.out.println("Simulation configured: " + vendors + " vendors, " + customers + " customers, and " + maxTickets + " tickets.");
    }

    public void startSimulation() {
        if (running.get()) {
            System.out.println("Simulation already running. Please stop the current simulation first.");
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

        // Create and start vendor threads
        for (int i = 1; i <= 3; i++) {
            Thread vendorThread = new Thread(() -> {
                while (running.get()) {
                    try {
                        addTickets(5);
                        Thread.sleep((long) (Math.random() * 2000 + 1000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            vendorThread.setName("Vendor-" + i);
            threads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 1; i <= 5; i++) {
            Thread customerThread = new Thread(() -> {
                while (running.get()) {
                    try {
                        buyTickets(1);
                        Thread.sleep((long) (Math.random() * 2000 + 1000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            customerThread.setName("Customer-" + i);
            threads.add(customerThread);
            customerThread.start();
        }
    }

    /**
     * Stops the simulation process.
     */
    public synchronized void stopSimulation() {
        if (!running.get()) {
            System.out.println("Simulation is not running.");
            return;
        }

        running.set(false);
        System.out.println("Stopping simulation...");
        threads.forEach(Thread::interrupt); // Interrupt all threads
        threads.clear();
        System.out.println("Simulation stopped.");
    }

    /**
     * Adds tickets to the pool.
     * @param count Number of tickets to add.
     */
    private synchronized void addTickets(int count) {
        availableTickets.addAndGet(count);
        System.out.println(Thread.currentThread().getName() + " added " + count + " tickets. Available tickets: " + availableTickets.get());
    }

    /**
     * Simulates customers buying tickets.
     * @param count Number of tickets to buy.
     */
    private synchronized void buyTickets(int count) {
        if (availableTickets.get() >= count) {
            availableTickets.addAndGet(-count);
            System.out.println(Thread.currentThread().getName() + " bought " + count + " ticket(s). Remaining tickets: " + availableTickets.get());
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to buy " + count + " ticket(s), but not enough tickets are available.");
        }
    }

    /**
     * Gets validated integer input from the user.
     * @param scanner Input scanner.
     * @param prompt  Input prompt.
     * @return A valid integer.
     */
    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Integer required. Please try again.");
                scanner.nextLine();
            }
        }
    }
}
