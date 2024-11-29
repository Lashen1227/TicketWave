package org.oop.model;

import java.io.Serializable;

/**
 * Represents the configuration settings for the Event Ticketing System.
 */
public class Configuration implements Serializable {
    private int id; // Unique ID for database storage
    private int totalTickets;
    private float ticketReleaseRate;
    private float customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {
    }

    public Configuration(int totalTickets, float ticketReleaseRate, float customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public float getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(float ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public float getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(float customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}
