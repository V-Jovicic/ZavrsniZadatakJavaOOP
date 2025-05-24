package models.cards;

import java.time.LocalDate;

public class Card {

    private String id; // The ID format in the db is "CXXXX" (X representing integer numbers).
    private String ownerUsername;
    private LocalDate cardValidFrom;
    private LocalDate cardValidUntil;
    private double balance;
    private String currentlyRentedVehicleId; // The format in the db is "VXXXX" (X representing integer numbers).
    private String rentHistory; // The format in the db is "RXXXX_RXXXX"
                                // (X representing integer numbers, underscores acting as separators if there is more than 1 rent in the history)

    public Card(String id, String ownerUserName, LocalDate cardValidFrom, LocalDate cardValidUntil, double balance, String currentlyRentedVehicleId, String rentHistory) {
        this.id = id;
        this.ownerUsername = ownerUserName;
        this.cardValidFrom = cardValidFrom;
        this.cardValidUntil = cardValidUntil;
        this.balance = balance;
        this.currentlyRentedVehicleId = currentlyRentedVehicleId;
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

    public LocalDate getCardValidFrom() {
        return cardValidFrom;
    }
    public void setCardValidFrom(LocalDate cardValidFrom) {
        this.cardValidFrom = cardValidFrom;
    }

    public LocalDate getCardValidUntil() {
        return cardValidUntil;
    }
    public void setCardValidUntil(LocalDate cardValidUntil) {
        this.cardValidUntil = cardValidUntil;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrentlyRentedVehicleId() {
        return currentlyRentedVehicleId;
    }
    public void setCurrentlyRentedVehicleId(String currentlyRentedVehicleId) {
        this.currentlyRentedVehicleId = currentlyRentedVehicleId;
    }

    public String getRentHistory() {
        return rentHistory;
    }
    public void setRentHistory(String rentHistory) {
        this.rentHistory = rentHistory;
    }
}
