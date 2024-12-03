package com.oop.backend.cli;

import com.oop.backend.config.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.backend.entity.Customer;
import com.oop.backend.entity.EventItem;
import com.oop.backend.entity.Ticket;
import com.oop.backend.entity.Vendor;
import com.oop.backend.service.CustomerService;
import com.oop.backend.service.EventService;
import com.oop.backend.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConfigCLI {
    private static final String API_URL = "http://localhost:8080/api/configuration";
    public static final Logger logger = LoggerFactory.getLogger(ConfigCLI.class);

    @Autowired
    private VendorService vendorService;
    @Autowired
    private EventService eventService;
    @Autowired
    private CustomerService customerService;

    private final Scanner scanner;
    public ConfigCLI() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        ConfigCLI cli = new ConfigCLI();
        cli.displayWelcome();
        cli.runCLI();
    }

    public void displayWelcome () {
        System.out.println("\n*****************************************");
        System.out.println("\n* Welcome to the Event Ticketing System *");
        System.out.println("\n*****************************************");
    }

    public void runCLI() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getValidatedChoice();

            switch (choice) {
                case 1:
                    addConfiguration();
                    break;
                case 2:
                    addNewVendor();
                    break;
                case 3:
                    addNewCustomer();;
                    break;
                case 4:
                    addNewEvent();
                    break;
                case 5:
                    viewListOfVendors();
                    break;
                case 6:
                    viewListOfCustomers();
                    break;
                case 7:
                    viewListOfEvents();
                    break;
                case 8:
                    listOfTicketsForTheEvent();
                    break;
                case 9:
                    buyTicket();
                    break;
                case 10:
                    releaseTickets();
                    break;
                case 11:
                    viewTicketPool();
                    break;
                case 12:
                    startProcess();
                    break;
                case 0:
                    System.out.println("Exiting program..");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.. Please try again.");
            }
        }
    }

    private void displayMenu() {
        String mainMenu = """
                \n---------- Main Menu ----------
                1. Add Configuration
                2. Add Vendor
                3. Add Customer
                4. Add Event
                5. List of Vendors
                6. List of Customers
                7. List of Events
                8. List of Tickets for Event
                9. Buy Ticket
                10. Release Tickets
                11. View the Ticket Pool
                12. Start
                0. Exit
                """;
        System.out.println(mainMenu);
        System.out.print("Enter your choice : ");
    }

    private int getValidatedChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Integer required.. Please try again.");
                scanner.nextLine();
                displayMenu();
            }
        }
    }

    private void addConfiguration() {
        int totalTickets = getIntInput("Enter Total Number of Tickets: ");
        float ticketReleaseRate = getFloatInput("Enter Ticket Release Rate: ");
        float customerRetrievalRate = getFloatInput("Enter Customer Retrieval Rate: ");

        int maxTicketCapacity;  // Ensure max ticket capacity is valid
        while (true) {
            maxTicketCapacity = getIntInput("Enter Maximum Ticket Capacity: ");
            if (totalTickets > maxTicketCapacity ) {
                System.out.println("Maximum ticket capacity should be greater than or equal to total tickets.");
            } else {
                break;
            }
        }
        Configuration config = new Configuration();
        config.setTotalTickets(totalTickets);
        config.setTicketReleaseRate(ticketReleaseRate);
        config.setCustomerRetrievalRate(customerRetrievalRate);
        config.setMaxTicketCapacity(maxTicketCapacity);

        try {
            saveConfigToServer(config);
            System.out.println("Configuration saved successfully.");
        } catch (Exception e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }

    private void addNewVendor() {
        String name = getStringInput("Enter vendor name: ");
        int ticketReleaseRate = getIntInput("Enter ticket release rate: ");
        Vendor vendor = new Vendor(name, getRandomeEmail(name), ticketReleaseRate);
        vendorService.createVendor(vendor);
        System.out.println("Successfully created the vendor.");
    }

    private void viewListOfVendors() {
        List<Vendor> vendors = vendorService.getAllVendors(true);
        System.out.println("-- List of Vendors --");
        for (Vendor vendor : vendors) {
            System.out.println("Vendor ID : " + vendor.getId() + ", Name : " + vendor.getName());
        }
    }

    private void addNewCustomer() {
        String name = getStringInput("Enter customer name: ");
        int ticketRetrievalRate = getIntInput("Enter ticket retrieval rate: ");
        Customer customer = new Customer(name, getRandomeEmail(name), ticketRetrievalRate);
        customerService.createCustomer(customer);
        System.out.println("Successfully created the customer.");
    }

    private void viewListOfCustomers() {
        List<Customer> customers = customerService.getAllCustomers(true);
        System.out.println("Customers List : ");
        for (Customer customer : customers) {
            System.out.println("Customer ID : " + customer.getId() + ", Name : " + customer.getName());
        }
    }

    private void addNewEvent() {
        long vendorId = getLongInput("Enter vendor ID: ");
        String eventName = getStringInput("Enter event name: ");
        int maxPoolSize = getIntInput("Enter maximum pool size: ");

        EventItem eventItem = new EventItem(eventName, vendorService.getVendorById(vendorId), true);
        eventService.createEvent(eventItem, maxPoolSize);
        eventItem.createTicketPool(maxPoolSize);
        System.out.println("Successfully created the event.");
    }

    private void viewListOfEvents() {
        List<EventItem> eventItems = eventService.getAllEvents(true);
        System.out.println("Events:");
        for (EventItem eventItem : eventItems) {
            System.out.println("ID: " + eventItem.getId() + ", Name: " + eventItem.getEventName());
        }
    }

    private void listOfTicketsForTheEvent() {
        long eventId = getLongInput("Enter the Event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            List<Ticket> tickets = eventItem.getTicketPool().getTickets();
            System.out.println("Tickets for event " + eventItem.getEventName() + ".");
            for (Ticket ticket : tickets) {
                System.out.println("  ID: " + ticket.getId() + ", Available?: " + ticket.isAvailable());
            }
        } else {
            System.out.println("Event not found. Please try again.");
        }
    }

    private void buyTicket() {
        long customerId = getLongInput("Enter the customer ID: ");
        long eventId = getLongInput("Enter the event ID: ");
        Customer customer = customerService.getCustomerById(customerId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (customer != null && eventItem != null) {
            System.out.println("Ticket purchase requested.");
            customerService.purchaseTicket(customer, eventId);
        } else {
            System.out.println("Customer or Event not found.");
        }
    }

    private void releaseTickets() {
        long vendorId = getLongInput("Enter vendor ID: ");
        long eventId = getLongInput("Enter event ID: ");
        Vendor vendor = vendorService.getVendorById(vendorId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (vendor != null && eventItem != null && eventItem.getVendor().getId().equals(vendorId)) {
            System.out.println("Tickets release requested.");
            vendorService.releaseTickets(vendor, eventId);
        } else {
            System.out.println("Vendor or Event not found, or they are not related.");
        }
    }

    private void viewTicketPool() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            System.out.println(eventItem.toString());
        } else {
            System.out.println("Event not found.");
        }
    }

    private void addValuesToStart() {
        System.out.println("Configure the simulation");
        int numOfVendors = getIntInput("Enter the number of vendors: ");
        int numOfCustomers = getIntInput("Enter the number of customers: ");
        System.out.println("Creating simulation data - Please wait, This may take a while.");

        for (int i = 0; i < numOfVendors; i++) {
            int ticketReleaseRate = (int) (Math.random() * 5) + 1;
            Vendor vendor = new Vendor("Vendor " + i, getRandomeEmail("Vendor " + i), ticketReleaseRate);
            vendorService.createVendor(vendor);
        }
        logger.info("Vendors are ready");

        for (int i = 0; i < numOfCustomers; i++) {
            int ticketRetrievalRate = (int) (Math.random() * 5) + 1;
            Customer customer = new Customer("Customer " + i, getRandomeEmail("Customer " + i), ticketRetrievalRate);
            customerService.createCustomer(customer);
        }
        logger.info("Customers are ready");

        for (int i = 0; i < numOfVendors; i++) {
            int numTicketsPerEvent = (int) (Math.random() * 10) + 1;
            Vendor vendor = vendorService.getAllVendors(true).get((int) (Math.random() * numOfVendors));
            EventItem eventItem = new EventItem("Event " + i, vendor, true);
            eventService.createEvent(eventItem, numTicketsPerEvent);
            eventItem.createTicketPool(numTicketsPerEvent);
        }
        System.out.println("[Create] " + "vendors - " + numOfVendors + " , customers - " + numOfCustomers + " & events - " + numOfVendors);
        System.out.println("System is ready to start the simulation.");
    }

    private void startProcess() {
        addValuesToStart();
        System.out.println("Starting the simulation. Press 'Enter' key to stop.");
        List<Customer> customers = customerService.getAllCustomers(true);
        List<Vendor> vendors = vendorService.getAllVendors(true);
        List<EventItem> events = eventService.getAllEvents(true);
        final boolean[] isSimulating = {true};

        for (Vendor vendor : vendors) {
            new Thread(new VendorSimulation(vendor, events, vendorService, isSimulating)).start();
        }

        for (Customer customer : customers) {
            new Thread(new CustomerSimulation(customer, events, customerService, isSimulating)).start();
        }
        scanner.nextLine();
        System.out.println("Stopping.");
        isSimulating[0] = false;
        Thread.currentThread().interrupt();
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                if (value < 0) {
                    System.out.println("Invalid input.. Please enter a positive integer.");
                } else {
                    scanner.nextLine(); // Consume newline
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.println("Integer required.. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private long getLongInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private float getFloatInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                float value = scanner.nextFloat();
                if (value < 0) {
                    System.out.println("Invalid input.. Please enter a positive number.");
                } else {
                    scanner.nextLine();
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.println("Decimal number required.. Please try again.");
                scanner.nextLine();
            }
        }
    }

    private String getRandomeEmail(String name) {
        return name.toLowerCase().replace(" ", "") + (int) (Math.random() * 1000) + "@example.com";
    }

    // Helper method to send configuration data to the server
    private void saveConfigToServer(Configuration config) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonConfig = mapper.writeValueAsString(config);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonConfig.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Configuration saved to server.");
        } else {
            throw new Exception("Server responded with code: " + responseCode);
        }
    }

    // Helper method to fetch configuration data from the server
    private Configuration fetchConfigurationFromServer() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(connection.getInputStream(), Configuration.class);
        } else {
            System.out.println("Failed to fetch configuration. Response code: " + connection.getResponseCode());
            return null;
        }
    }

    // Helper method to start the simulation
    public void start() {
        runCLI();
    }
}
