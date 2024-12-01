package com.oop.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class EventItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private boolean isSimulated;
    private double ticketPrice;

    @Column(length = 500)
    private String eventName;
    @Column(length = 500)
    private String eventLocation;
    @Column(length = 500)
    private String eventDate;
    @Column(length = 500)
    private String eventTime;
    @Column(length = 1000)
    private String image;
    @Column(length = 2000)
    private String details;

    @ManyToOne
    @JoinColumn(name = "vendor_id") private Vendor vendor;

    @OneToOne
    @JoinColumn(name = "pool_id") private TicketPool ticketPool;

    public EventItem() {}

    public EventItem(String eventName, String eventLocation, String eventDate, String eventTime, double ticketPrice, String details, String image, Vendor vendor) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.vendor = vendor;
        this.details = details;
        this.image = image;
        this.isSimulated = false;
    }

    public EventItem(String eventName, Vendor vendor, boolean isSimulated) {
        this.eventName = eventName;
        this.vendor = vendor;
        this.isSimulated = isSimulated;
    }

    public EventItem(String eventName, String eventLocation, String eventDate, String eventTime, double ticketPrice, String details, String image) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.details = details;
        this.image = image;
        this.isSimulated = false;
    }

    public EventItem(String eventName, String eventLocation, String eventDate, String eventTime, double ticketPrice, String details, String image, Long vendorId, String vendorName) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.details = details;
        this.image = image;
        this.isSimulated = false;
    }

    public void createTicketPool(int maxPoolSize) {
        this.ticketPool = new TicketPool(maxPoolSize, this);
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public double getTicketPrice() {
        return this.ticketPrice;
    }

    public Long getId() {
        return this.eventId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Long getEventId() {
        return this.eventId;
    }

    public String getEventLocation() {
        return this.eventLocation;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public String getEventTime() {
        return this.eventTime;
    }

    public boolean isSimulated() {
        return this.isSimulated;
    }

    public String getDetails() {
        return this.details;
    }

    public String getImage() {
        return this.image;
    }

    @Override
    public String toString() {
        return "EventItem{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", isSimulated=" + isSimulated +
                ", vendor=" + vendor.getName() +
                ", availableTickets=" + ticketPool.getAvailableTickets() +
                '}';
    }

    public String getName() {
        return this.eventName;
    }

    public int getAvailableTickets() {
        return ticketPool.getAvailableTickets();
    }

    public String getImageUrl() {
        return this.image;
    }

    public String getDateTime() {
        return this.eventDate + " " + this.eventTime;
    }
    // ... getters and setters ...
}