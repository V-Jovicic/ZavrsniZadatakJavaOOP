package services;

import enums.AdditionalEquipment;
import enums.State;
import models.cards.Card;
import models.rents.Rent;
import models.users.Renter;
import models.users.User;
import models.vehicles.Vehicle;
import util.Getters;
import util.SearchUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlatformHelpers {

    DatabaseService dbService;
    Scanner scanner;

    public PlatformHelpers(DatabaseService dbService, Scanner scanner) {
        this.dbService = dbService;
        this.scanner = scanner;
    }

    public void userLoggedInMenu(User activeUser) {
        while (true) {
            System.out.println("===============");
            System.out.println("1. Pretraga vozila");

            // Menu options for Renters
            Card card = null;
            if (activeUser.getType().equalsIgnoreCase("renter")) {
                card = Getters.getCardById(dbService, ((Renter) activeUser).getCardId());
                System.out.println("2. Deponovanje sredstava na racun");
                System.out.println("3. Iznajmljivanje vozila");
                if (card == null) {
                    System.out.println("===============");
                    System.out.println("Greska pri dobijanju NGO kartice!");
                    return;
                }
                if (!card.getCurrentlyRentedVehicleId().equalsIgnoreCase("")) {
                    System.out.println("4. Vracanje vozila");
                }
            }

            // Menu options for Servicemen
            if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                System.out.println("2. Pregled vozila");
                System.out.println("3. Popravka vozila");
            }

            System.out.println("0. Odjava");

            System.out.println("===============");
            System.out.print("Izaberite opciju: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        List<Vehicle> resultOfSearch = SearchUtils.vehicleSearch(scanner, dbService.getVehiclesArr(), dbService.getRentsArr(), true);
                        int counter = 0;
                        System.out.println("===============");
                        System.out.println("Lista vozila:");
                        if (resultOfSearch == null || resultOfSearch.isEmpty()) {
                            System.out.println("===============");
                            System.out.println("Nema odgovarajucih rezultata!");
                        } else {
                            for (Vehicle vehicle : resultOfSearch) {
                                System.out.println(++counter + ". " + vehicle);
                            }
                        }
                    }
                    case 2 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            if (card == null) {
                                System.out.println("===============");
                                System.out.println("Greska pri dobijanju NGO kartice!");
                                return;
                            }
                            depositBalance(card);
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            List<Vehicle> resultOfSearch = SearchUtils.vehicleSearchByService(dbService.getVehiclesArr(), dbService.getRentsArr(), false);
                            System.out.println("===============");
                            System.out.println("Lista vozila:");
                            if (resultOfSearch.isEmpty()) {
                                System.out.println("===============");
                                System.out.println("Nema odgovarajucih rezultata!");
                                System.out.println("0. Povratak nazad");
                            } else {
                                vehicleCheckUp(resultOfSearch);
                            }
                        }
                    }
                    case 3 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            vehicleRent(activeUser);
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            State[] targetStates = {State.MALO_OSTECENJE, State.VELIKO_OSTECENJE};
                            List<Vehicle> resultOfStateSearch = SearchUtils.vehicleSearchByState(dbService.getVehiclesArr(), targetStates);
                            List<Vehicle> finalResultOfSearchAfterAvailability = SearchUtils.vehicleSearchByAvailability(resultOfStateSearch, false);
                            System.out.println("===============");
                            System.out.println("Lista vozila:");
                            if (finalResultOfSearchAfterAvailability.isEmpty()) {
                                System.out.println("===============");
                                System.out.println("Nema odgovarajucih rezultata!");
                                System.out.println("0. Povratak nazad");
                            } else {
                                vehicleFix(finalResultOfSearchAfterAvailability);
                            }
                        }
                    }
                    case 4 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            if (card == null) {
                                System.out.println("===============");
                                System.out.println("Greska pri dobijanju NGO kartice!");
                                return;
                            }
                            if (!card.getCurrentlyRentedVehicleId().equalsIgnoreCase("")) {
                                vehicleReturn(activeUser);
                            }
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            System.out.println("===============");
                            System.out.println("Nepostojeca opcija!");
                        }
                    }
                    case 0 -> {
                        System.out.println("===============");
                        System.out.println("Uspesno ste odjavljeni!");
                        return;
                    }
                    default -> {
                        System.out.println("===============");
                        System.out.println("Nepostojeca opcija!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("===============");
                System.out.println("Molimo unesite broj kao opciju!");
            }
        }
    }

    private void depositBalance(Card paramCard) {
        System.out.println("===============");
        System.out.print("Unesite zeljeni iznos za deponovanje: ");
        try {
            // We ask for the new balance, update the value in the paramCard object,
            // and then re-set the cardsArr in dbService, which writes the change to the database
            int newBalance = Integer.parseInt(scanner.nextLine());
            paramCard.setBalance(newBalance);
            List<Card> newCardsArr = dbService.getCardsArr();
            dbService.setCardsArr(newCardsArr);
        } catch (NumberFormatException e) {
            System.out.println("Molimo unesite broj kao opciju!");
        }
    }

    private void vehicleCheckUp(List<Vehicle> resultOfSearch) {
        int counter = 0;
        Vehicle targetVehicle = null;
        for (Vehicle vehicle : resultOfSearch) {
            System.out.println(++counter + ". " + vehicle);
        }
        System.out.println("0. Povratak nazad");
        System.out.println("===============");
        System.out.print("Unesite ID vozila koje zelite da pregledate: ");
        try {
            int targetVehicleCardinalNumber = Integer.parseInt(scanner.nextLine());
            counter = 0;
            // If the users wishes to return without any actions, we simply exit
            if (targetVehicleCardinalNumber == 0) {
                return;
            }
            // We fetch the selected vehicle object
            for (Vehicle vehicle : resultOfSearch) {
                if (++counter == targetVehicleCardinalNumber) {
                    targetVehicle = vehicle;
                    break;
                }
            }
            // Checking if the vehicle was found, if not we simply exit
            if (targetVehicle == null) {
                System.out.println("Greska pri odabiru vozila, molimo proverite da li je ID tacno unet.");
                return;
            }
            System.out.println("Moguca stanja:");
            System.out.println("1. Bez ostecenja");
            System.out.println("2. Malo ostecenje");
            System.out.println("3. Veliko ostecenje");
            System.out.println("4. Neupotrebljivo");
            System.out.println("0. Povratak nazad");
            System.out.println("===============");
            System.out.print("Unesite stanje vozila: ");
            try {
                int servicemanVehicleInspectionChoice = Integer.parseInt(scanner.nextLine());
                switch (servicemanVehicleInspectionChoice) {
                    case 1 -> targetVehicle.fixVehicle(State.BEZ_OSTECENJA);
                    case 2 -> targetVehicle.fixVehicle(State.MALO_OSTECENJE);
                    case 3 -> targetVehicle.fixVehicle(State.VELIKO_OSTECENJE);
                    case 4 -> targetVehicle.fixVehicle(State.NEUPOTREBLJIVO);
                    case 0 -> {
                    }
                    default -> System.out.println("Nepostojeca opcija!");
                }

                // We set the live vehicles array, which automatically updates the database.
                dbService.setVehiclesArr(dbService.getVehiclesArr());

            } catch (NumberFormatException e) {
                System.out.println("Molimo unesite broj kao opciju!");
            }
        } catch (NumberFormatException e) {
            System.out.println("===============");
            System.out.println("Molimo unesite broj!");
        }
    }

    private void vehicleFix(List<Vehicle> resultOfSearch) {
        int counter = 0;
        for (Vehicle vehicle : resultOfSearch) {
            System.out.println(++counter + ". " + vehicle);
        }
        System.out.println("0. Povratak nazad");
        System.out.println("===============");
        System.out.print("Unesite redni broj vozila koje zelite da popravite: ");
        try {
            int targetVehicleCardinalNumber = Integer.parseInt(scanner.nextLine());
            counter = 0;
            // If the users wishes to return without any actions, we simply exit
            if (targetVehicleCardinalNumber == 0) {
                return;
            }
            for (Vehicle vehicle : resultOfSearch) {
                if (++counter == targetVehicleCardinalNumber) {
                    vehicle.fixVehicle(State.BEZ_OSTECENJA);
                    for (Rent rent : dbService.getRentsArr()) {
                        if (rent.getRentedVehicleId().equalsIgnoreCase(vehicle.getId())) {
                            rent.setServiceDone(true);
                            break;
                        }
                    }
                    break;
                }
            }

            // We set the live vehicles & rents arrays, which automatically updates the database.
            dbService.setVehiclesArr(dbService.getVehiclesArr());
            dbService.setRentsArr(dbService.getRentsArr());

        } catch (NumberFormatException e) {
            System.out.println("===============");
            System.out.println("Molimo unesite broj!");
        }
    }

    private void vehicleRent(User activeUser) {
        // First we need to run all our checks

        Card activeUsersCard = Getters.getCardById(dbService, ((Renter) activeUser).getCardId());
        // We check if card fetching was successful
        if (activeUsersCard == null) {
            System.out.println("===============");
            System.out.println("Greska pri pronalazenju NGO kartice. Kontaktirajte podrsku.");
            return;
        }
        // Checking if the card is still valid (not expired)
        if (activeUsersCard.getCardValidUntil().isBefore(LocalDate.now())) {
            System.out.println("===============");
            System.out.println("Kartica Vam je istekla! Molimo obnovite je.");
            return;
        }
        // Checking if we have enough balance
        if (activeUsersCard.getBalance() <= 0) {
            System.out.println("===============");
            System.out.println("Nemate dovoljno novcanih sredstava! Molimo dopunite karticu.");
            return;
        }
        // Checking if the user already has a rented vehicle
        if (!activeUsersCard.getCurrentlyRentedVehicleId().equalsIgnoreCase("null")) {
            System.out.println("===============");
            System.out.println("Vec ste iznajmili jedno vozilo. Prvo ga vratite da biste mogli ponovo iznajmljivati.");
            return;
        }

        // After checks are complete, we can proceed with the method body
        List<Vehicle> resultOfSearch = SearchUtils.vehicleSearch(scanner, dbService.getVehiclesArr(), dbService.getRentsArr(), false);
        // If the user decides to return from the search instead of searching,
        // the returned value is null, so we handle that and don't proceed.
        if (resultOfSearch == null) {
            return;
        }
        int counter = 0;
        Vehicle targetVehicle = null;
        List<AdditionalEquipment> listOfEquipment = new ArrayList<>();

        System.out.println("===============");
        System.out.println("Lista vozila:");
        if (resultOfSearch.isEmpty()) {
            System.out.println("===============");
            System.out.println("Nema odgovarajucih rezultata!");
        } else {
            for (Vehicle vehicle : resultOfSearch) {
                System.out.println(++counter + ". " + vehicle);
            }
            System.out.println("0. Povratak nazad");
            System.out.println("===============");
            System.out.print("Unesite redni broj vozila koje zelite da iznajmite: ");
            try {
                int targetVehicleCardinalNumber = Integer.parseInt(scanner.nextLine());
                if (targetVehicleCardinalNumber == 0) {
                    return;
                }
                counter = 0;
                for (Vehicle vehicle : resultOfSearch) {
                    if (++counter == targetVehicleCardinalNumber) {
                        targetVehicle = vehicle;
                        break;
                    }
                }
                // We check if the vehicle with that cardinal # was found
                if (targetVehicle == null) {
                    System.out.println("===============");
                    System.out.println("Greska pri pronalazenju vozila. Molimo proverite da li je redni broj tacno unesen.");
                    return;
                }
                // We ask the user if they wish to add any additional equipment to their rental
                while (true) {
                    int equipmentCounter = 0;
                    System.out.println("===============");
                    System.out.println("Lista dodatne opreme:");
                    for (AdditionalEquipment equipment : AdditionalEquipment.values()) {
                        System.out.println(++equipmentCounter + ". " + equipment.toString().replace("_", " "));
                    }
                    System.out.println("0. Iznajmi");
                    System.out.println("===============");
                    System.out.print("Unesite redni broj dodatne opreme koju zelite: ");
                    try {
                        int targetAddtlEquipment = Integer.parseInt(scanner.nextLine());
                        if (targetAddtlEquipment == 0) {
                            break;
                        }
                        if (targetAddtlEquipment < 1 || targetAddtlEquipment > AdditionalEquipment.values().length) {
                            System.out.println("===============");
                            System.out.println("Nepostojeca opcija");
                            continue;
                        }
                        equipmentCounter = 0;
                        for (AdditionalEquipment equipment : AdditionalEquipment.values()) {
                            if (++equipmentCounter == targetAddtlEquipment) {
                                if (listOfEquipment.contains(equipment)) {
                                    System.out.println("===============");
                                    System.out.println("Vec ste dodali ovu opremu!");
                                }
                                listOfEquipment.add(equipment);
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("===============");
                        System.out.println("Molimo unesite broj!");
                    }
                }
                targetVehicle.setOwnerUsername(activeUser.getUsername());
                targetVehicle.rentVehicle();

                // Generating new rent object and preparing new list for the setRentsArr call below
                Rent newRent = dbService.generateRentRecord(activeUser.getUsername(), targetVehicle.getId(), listOfEquipment);
                List<Rent> newRentsArr = dbService.getRentsArr();
                newRentsArr.add(newRent);

                // Card updates section
                String updatedRentHistory = activeUsersCard.getRentHistory();
                updatedRentHistory = updatedRentHistory + "_" + newRent.getId();
                activeUsersCard.setCurrentlyRentedVehicleId(targetVehicle.getId());
                activeUsersCard.setRentHistory(updatedRentHistory);

                // We set every array affected (Vehicles, Cards, Rents) to update the database.
                dbService.setVehiclesArr(dbService.getVehiclesArr());
                dbService.setCardsArr(dbService.getCardsArr());
                dbService.setRentsArr(newRentsArr);
            } catch (NumberFormatException e) {
                System.out.println("===============");
                System.out.println("Molimo unesite broj!");
            }
        }
    }

    private void vehicleReturn(User activeUser) {
        String cardId = ((Renter) activeUser).getCardId();
        Card activeUsersCard = Getters.getCardById(dbService, cardId);
        if (activeUsersCard == null) {
            System.out.println("===============");
            System.out.println("Greska pri ucitavanju NGO kartice.");
            return;
        }
        if (activeUsersCard.getCurrentlyRentedVehicleId().equalsIgnoreCase("null")) {
            System.out.println("===============");
            System.out.println("Niste iznajmili vozilo!");
            return;
        }

        // Dummy text to honour the specification's request for prompting the user
        // to select vehicle based on cardinal number from a list of rented vehicles
        // even though the user may only have 1 vehicle active as rented.
        Vehicle targetVehicle = Getters.getVehicleById(dbService, activeUsersCard.getCurrentlyRentedVehicleId());
        if (targetVehicle == null) {
            System.out.println("===============");
            System.out.println("Greska pri selekciji vozila.");
            return;
        }
        System.out.println("===============");
        System.out.println("Lista iznajmljenih vozila:");
        System.out.println("1. " + targetVehicle);
        System.out.println("===============");
        System.out.print("Izaberite vozilo koje zelite da vratite: ");
        try {
            int targetVehicleCardinalNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("===============");
            System.out.println("Molimo unesite broj!");
        }

        for (Rent targetRent : dbService.getRentsArr()) {
            if (targetRent.getRenterUsername().equalsIgnoreCase(activeUser.getUsername()) && targetRent.getDateTimeRentedUntil() == null) {
                targetRent.setDateTimeRentedUntil(LocalDateTime.now());
                activeUsersCard.setCurrentlyRentedVehicleId("null");
                targetVehicle.returnVehicle();
                Duration durationBetweenStartAndEndRentDate = Duration.between(targetRent.getDateTimeRentedFrom(), targetRent.getDateTimeRentedUntil());
                double amountOwedByRenter = (double) durationBetweenStartAndEndRentDate.toHours() * targetVehicle.getPerHourRate();
                double updatedBalance = activeUsersCard.getBalance() - amountOwedByRenter;
                activeUsersCard.setBalance(updatedBalance);
                if (updatedBalance < 0) {
                    System.out.println("===============");
                    System.out.println("Kartica Vam je zaduzena! Dalja iznajmljivanja nece biti moguca do dopune sredstava na pozitivnu vrednost!");
                }
                dbService.setRentsArr(dbService.getRentsArr());
                dbService.setCardsArr(dbService.getCardsArr());
                dbService.setVehiclesArr(dbService.getVehiclesArr());

            }
        }
    }
}
