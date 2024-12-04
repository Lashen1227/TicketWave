package org.oop.model;

public class AppUser {
    private long id;
    private String userType;
    private String email;
    private boolean isSimulated;
    private String name;
    private String password;
    private int ticketRetrievalRate;
    private int ticketReleaseRate;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSimulated() {
        return isSimulated;
    }

    public void setSimulated(boolean simulated) {
        isSimulated = simulated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ", UserType: " + userType
                + ", Name: " + name
                + ", Email: " + email;
//                + ", IsSimulated: " + isSimulated
//                + ", TicketRetrievalRate: " + ticketRetrievalRate
//                + ", TicketReleaseRate: " + ticketReleaseRate;
    }
}
