package services;

import enums.State;
import models.cards.Card;
import models.rents.Rent;
import models.users.*;
import models.vehicles.Bicycle;
import models.vehicles.Scooter;
import models.vehicles.Vehicle;

import java.io.*;
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
    private List<Rent> rentsArr = new ArrayList<>();

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
                } else {
                    usersArr.add(new UnknownUser(username, password, name, surname, type));
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
                    int highestSpeed = Integer.parseInt(data[10]);
                    int batteryDuration = Integer.parseInt(data[11]);
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


        // Firstly, we need to empty out the database, so that we can rewrite it
        // We don't keep appending since that doesn't cover deletion of entries
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Doslo je do greske prilikom azuriranja baze podataka!");
            System.exit(0);
        }

        // We loop through the new array of users, create a query line that we will append to the database
        for (User user : usersArr) {
            StringBuilder query = new StringBuilder(
                    user.getUsername() + ',' +
                            user.getPassword() + ',' +
                            user.getName() + ',' +
                            user.getSurname() + ',');

            switch (user.getType().toLowerCase()) {
                case "renter" -> query.append("renter" + ',')
                        .append(((Renter) user).getCardId())
                        .append(',')
                        .append("null");
                case "serviceman" -> query.append("serviceman,null,null");
                case "owner" -> {
                    StringBuilder vehiclesList = new StringBuilder();
                    boolean appended = false;
                    // We add each vehicle that is owned by a single owner
                    for (Vehicle vehicle : vehiclesArr) {
                        if (vehicle.getOwnerUsername().equalsIgnoreCase(user.getUsername())) {
                            vehiclesList.append(vehicle.getId()).append("_");
                        }
                        // If the user owns no vehicles, we write null.
                        // We do this because our load functions use the .split() method,
                        // which loses data when the last value is an empty character (in the case of "xxxx,xxxx,xxxx,xxxx,")
                        appended = true;
                    }
                    if (appended) {
                        query.append("owner,null,").append(vehiclesList, 0, vehiclesList.length() - 1);
                    } else {
                        query.append("owner,null,null");
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV, true))) {
                writer.write(String.valueOf(query));
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Doslo je do greske prilikom azuriranja baze podataka!");
                System.exit(0);
            }
        }
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

        // Firstly, we need to empty out the database, so that we can rewrite it
        // We don't keep appending since that doesn't cover deletion of entries
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARDS_CSV))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Doslo je do greske prilikom azuriranja baze podataka!");
            System.exit(0);
        }

        for (Card card : cardsArr) {
            StringBuilder query = new StringBuilder(
                    card.getId() + ',' +
                            card.getOwnerUsername() + ',' +
                            card.getCardValidFrom().toString() + ',' +
                            card.getCardValidUntil().toString() + ',' +
                            card.getBalance() + ',' +
                            card.getCurrentlyRentedVehicleId() + ','
            );

            int counter = 0;
            boolean appended = false;
            StringBuilder rentHistory = new StringBuilder();
            for (Rent rent : rentsArr) {
                if (card.getOwnerUsername().equalsIgnoreCase(rent.getRenterUsername())) {
                    if (++counter != rentsArr.size()) {
                        rentHistory.append(rent.getId()).append('_');
                    } else {
                        rentHistory.append(rent.getId());
                    }
                    appended = true;
                }
            }
            // If the card has no rent history, we write null.
            // We do this because our load functions use the .split() method,
            // which loses data when the last value is an empty character (in the case of "xxxx,xxxx,xxxx,xxxx,")
            if (!appended) {
                query.append("null");
            } else {
                query.append(rentHistory);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARDS_CSV, true))) {
                writer.write(String.valueOf(query));
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Doslo je do greske prilikom azuriranja baze podataka!");
                System.exit(0);
            }
        }
    }

    public List<Rent> getRentsArr() {
        return rentsArr;
    }

    public void setRentsArr(List<Rent> rentsArr) {
        this.rentsArr = rentsArr;
    }

    boolean usernameAlreadyExists(String username) {
        for (User targetUser : getUsersArr()) {
            if (username.equalsIgnoreCase(targetUser.getUsername())) return true;
        }
        return false;
    }

    Card generateCard(String ownerUsername) {
        int counter = cardsArr.size() + 1;
        String id = "C" + String.format("%04d", counter);
        LocalDate cardValidFrom = LocalDate.now();
        LocalDate cardValidUntil = LocalDate.now().plusYears(1);
        return new Card(id, ownerUsername, cardValidFrom, cardValidUntil, 0, "", "");
    }

}
