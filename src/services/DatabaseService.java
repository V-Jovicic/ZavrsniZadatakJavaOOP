package services;

import enums.State;
import models.cards.Card;
import models.users.Owner;
import models.users.Renter;
import models.users.Serviceman;
import models.users.User;
import models.vehicles.Bicycle;
import models.vehicles.Scooter;
import models.vehicles.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String USERS_CSV = "data/korisnici.csv";
    private static final String VEHICLES_CSV = "data/vozila.csv";
    private static final String CARDS_CSV = "data/kartice.csv";
    private static final String RENTS_CSV = "data/najmovi.csv";

    private List<User> usersArr = new ArrayList<>();
    private List<Vehicle> vehiclesArr = new ArrayList<>();
    private List<Card> cardsArr = new ArrayList<>();

    public DatabaseService() {

        usersArr = loadUsers();
        vehiclesArr = loadVehicles();
        cardsArr = loadCards();

        if (usersArr == null && vehiclesArr == null && cardsArr == null) {
            // If we are unable to read the database, the rest of the program won't function so we immediately terminate.
            System.out.println("Greska pri ucitavanju iz baze podataka!");
            System.exit(0);
        }

    }

    private List<User> loadUsers() {

        // We try to read the users .csv file.
        // If successful, return the List of users
        // If not successful, return null. This is checked in the constructor after the call of this function.

        List<User> usersArr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                String name = data[2];
                String surname = data[3];
                String type = data[4];
                String cardId = data[5]; // CAN BE EMPTY
                String vehicleArr = data[6]; // CAN BE EMPTY

                if (type.equalsIgnoreCase("Renter")) {
                    usersArr.add(new Renter(username, password, name, surname, type, cardId));
                }else if (type.equalsIgnoreCase("Serviceman")) {
                    usersArr.add(new Serviceman(username, password, name, surname, type));
                }else if (type.equalsIgnoreCase("Owner")) {
                    usersArr.add(new Owner(username, password, name, surname, type, vehicleArr));
                }
            }
        } catch (IOException e) {
            System.out.println("Greška pri učitavanju korisnika: " + e.getMessage());
            return null;
        }
        return usersArr;
    }

    private List<Vehicle> loadVehicles() {

        // We try to read the vehicle .csv file.
        // If successful, return the List of vehicles
        // If not successful, return null. This is checked in the constructor after the call of this function.

        List<Vehicle> vehiclesArr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(VEHICLES_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String type = data[1];
                String ownerUsername = data[2];
                int perHourRate = Integer.parseInt(data[3]);
                int wheelSize = Integer.parseInt(data[4]);
                int maxLoadWeight = Integer.parseInt(data[5]);
                State state = State.valueOf(data[6]);
                boolean isRented = Boolean.parseBoolean(data[7]);

                if (type.equalsIgnoreCase("Bicycle")) {
                    int numOfGears = Integer.parseInt(data[8]);
                    double height = Double.parseDouble(data[9]);
                    vehiclesArr.add(new Bicycle(id, type, ownerUsername, perHourRate, wheelSize, maxLoadWeight, state, isRented, numOfGears, height));
                }else if (type.equalsIgnoreCase("Scooter")) {
                    int highestSpeed = Integer.parseInt(data[11]);
                    int batteryDuration = Integer.parseInt(data[12]);
                    vehiclesArr.add(new Scooter(id, type, ownerUsername, perHourRate, wheelSize, maxLoadWeight, state, isRented, highestSpeed, batteryDuration));
                }
            }
        } catch (IOException e) {
            System.out.println("Greška pri učitavanju vozila: " + e.getMessage());
            return null;
        }
        return vehiclesArr;
    }

    private List<Card> loadCards() {

        // We try to read the cards .csv file.
        // If successful, return the List of cards
        // If not successful, return null. This is checked in the constructor after the call of this function.

        List<Card> cardsArr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CARDS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String ownerUsername = data[1];
                LocalDate cardValidFrom = LocalDate.parse(data[2]);
                LocalDate cardValidUntil = LocalDate.parse(data[3]);
                double balance = Double.parseDouble(data[4]);
                String currentlyRentedVehicleId = data[5];
                String rentHistory = data[6];

                cardsArr.add(new Card (id, ownerUsername, cardValidFrom, cardValidUntil, balance, currentlyRentedVehicleId, rentHistory));
            }
        } catch (IOException e) {
            System.out.println("Greška pri učitavanju kartica: " + e.getMessage());
            return null;
        }
        return cardsArr;
    }

    // Our setter methods will also act as writer methods to the .csv files,
    // as every time we want to update the app's Lists, we also want to update the db.
    // TODO

    public List<User> getUsersArr() {
        return usersArr;
    }
    public void setUsersArr(List<User> usersArr) {
        this.usersArr = usersArr;
    }

    public List<Vehicle> getVehiclesArr() {
        return vehiclesArr;
    }
    public void setVehiclesArr(List<Vehicle> vehiclesArr) {
        this.vehiclesArr = vehiclesArr;
    }

    public List<Card> getCardsArr() {
        return cardsArr;
    }
    public void setCardsArr(List<Card> cardsArr) {
        this.cardsArr = cardsArr;
    }

    boolean usernameAlreadyExists(String username) {
        for (User targetUser : getUsersArr()) {
            if (username.equalsIgnoreCase(targetUser.getUsername())) return true;
        }
        return false;
    }

    Card generateCard(String ownerUsername) {
        int counter = cardsArr.size();
        String id = "C" + String.format("%04d", counter);
        LocalDate cardValidFrom = LocalDate.now();
        LocalDate cardValidUntil = LocalDate.now().plusYears(1);
        return new Card(id, ownerUsername, cardValidFrom, cardValidUntil, 0, "", "");
    }

}
