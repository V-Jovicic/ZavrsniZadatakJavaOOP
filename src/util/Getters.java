package util;

import models.cards.Card;
import models.rents.Rent;
import models.users.User;
import models.vehicles.Vehicle;
import services.DatabaseService;

public class Getters {

    public static User getUserByUsername(DatabaseService dbService, String username) {
        for (User user : dbService.getUsersArr()) {
            if (user.getUsername().equalsIgnoreCase(username)) return user;
        }
        return null;
    }

    public static Vehicle getVehicleById(DatabaseService dbService, String vehicleId) {
        for (Vehicle vehicle : dbService.getVehiclesArr()) {
            if (vehicle.getId().equalsIgnoreCase(vehicleId)) return vehicle;
        }
        return null;
    }

    public static Card getCardById(DatabaseService dbService, String cardId) {
        for (Card card : dbService.getCardsArr()) {
            if (card.getId().equalsIgnoreCase(cardId)) return card;
        }
        return null;
    }

    public static Rent getRentById(DatabaseService dbService, String rentId) {
        for (Rent rent : dbService.getRentsArr()) {
            if (rent.getId().equalsIgnoreCase(rentId)) return rent;
        }
        return null;
    }

}
