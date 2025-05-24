package models.cards;

import java.util.Date;

public class Card {

    private String id; // The ID format in the db is "CXXXX" (X representing integer numbers).
    private String ownerUsername;
    private Date cardValidFrom;
    private Date cardValidUntil;
    private double balance;
    private String currentlyRentedVehicle; // The format in the db is "VXXXX" (X representing integer numbers).
    private String rentHistory; // The format in the db is "RXXXX_RXXXX"
                                // (X representing integer numbers, underscores acting as separators if there is more than 1 rent in the history)

    public Card(String id, String ownerUserName, Date cardValidFrom, Date cardValidUntil, double balance, String currentlyRentedVehicle, String rentHistory) {
        this.id = id;
        this.ownerUsername = ownerUserName;
        this.cardValidFrom = cardValidFrom;
        this.cardValidUntil = cardValidUntil;
        this.balance = balance;
        this.currentlyRentedVehicle = currentlyRentedVehicle;
        this.rentHistory = rentHistory;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Date getCardValidFrom() {
        return cardValidFrom;
    }
    public void setCardValidFrom(Date cardValidFrom) {
        this.cardValidFrom = cardValidFrom;
    }

    public Date getCardValidUntil() {
        return cardValidUntil;
    }
    public void setCardValidUntil(Date cardValidUntil) {
        this.cardValidUntil = cardValidUntil;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrentlyRentedVehicle() {
        return currentlyRentedVehicle;
    }
    public void setCurrentlyRentedVehicle(String currentlyRentedVehicle) {
        this.currentlyRentedVehicle = currentlyRentedVehicle;
    }

    public String getRentHistory() {
        return rentHistory;
    }
    public void setRentHistory(String rentHistory) {
        this.rentHistory = rentHistory;
    }
}
