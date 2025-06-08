package services;

import models.cards.Card;
import models.users.Renter;
import models.users.User;
import models.vehicles.Vehicle;
import util.Getters;
import util.SearchUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PlatformHelpers {

    DatabaseService dbService;
    Scanner scanner;

    public PlatformHelpers(DatabaseService dbService, Scanner scanner) {
        this.dbService = dbService;
        this.scanner = scanner;
    }

    // TODO
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
                assert card != null;
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

            try {
                System.out.print("Izaberite opciju: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        List<Vehicle> resultOfSearch = SearchUtils.vehicleSearch(scanner, dbService.getVehiclesArr(), dbService.getRentsArr());
                        int counter = 0;
                        System.out.println("===============");
                        System.out.println("Lista vozila:");
                        if (resultOfSearch == null) {
                            System.out.println("Nema odgovarajucih rezultata!");
                            System.out.println("0. Povratak nazad");
                        } else {
                            for (Vehicle vehicle : resultOfSearch) {
                                System.out.println(++counter + ". " + vehicle);
                            }
                        }
                    }
                    case 2 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            assert card != null; // Compile-time assurance, since we only reach this point is the user is a renter
                            depositBalance(card);
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
//                            pregledVozila(); // TODO
                        }
                    }
                    case 3 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
//                            iznajmljivanjeVozila(); // TODO
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
//                            popravkaVozila(); // TODO
                        }
                    }
                    case 4 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            assert card != null; // Compile-time assurance, since we only reach this point is the user is a renter
                            if (!card.getCurrentlyRentedVehicleId().equalsIgnoreCase("")) {
//                                vracanjeVozila(); // TODO
                            }
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            System.out.println("Nepostojeca opcija!");
                        }
                    }
                    case 0 -> {
                        System.out.println("Uspesno ste odjavljeni!");
                        return;
                    }
                    default -> System.out.println("Nepostojeca opcija!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Molimo unesite broj kao opciju!");
            }
        }
    }

    public void depositBalance(Card paramCard) {
        System.out.print("Unesite zeljeni iznos za deponovanje: ");
        try {
            // We ask for the new balance, update the value in the paramCard object,
            // and then re-set the cardsArr in dbService, which writes the change to the database
            int newBalance = scanner.nextInt();
            paramCard.setBalance(newBalance);
            List<Card> newCardsArr = dbService.getCardsArr();
            dbService.setCardsArr(newCardsArr);
        } catch (InputMismatchException e) {
            System.out.println("Molimo unesite broj kao opciju!");
        }
    }

//    public void popravkaVozila() {
//        // TODO (fix params once function in place)
//        pretragaVozila(Stanje == "veliko_ostecenje" && Stanje == "malo_ostecenje");
//        System.out.print("Izaberite ID vozila koje zelite da popravite: ");
//        try {
//            // Browsing by vehicle ID as it guarantees we update the correct vehicle.
//            // We could alternatively browse by ordinal values. However, this is inconsistent as we cannot guarantee vehicles will remain in the same order each search.
//            String choice = scanner.next();
//            List<Vehicle> newVehiclesArr = dbService.getVehiclesArr();
//            for (Vehicle vehicle : newVehiclesArr) {
//                // We check to see if the user actually inputted an ID from the displayed list of vehicles
//                if (vehicle.getId().equalsIgnoreCase(choice) || (vehicle.checkVehicleState() != State.BEZ_OSTECENJA && vehicle.checkVehicleState() != State.NEUPOTREBLJIVO)) {
//                    vehicle.fixVehicle(State.BEZ_OSTECENJA);
//                }
//            }
//            // We set the live vehicles array, which automatically updated the database.
//            dbService.setVehiclesArr(newVehiclesArr);
//        } catch (Exception e) {
//            System.out.println("Doslo je do greske pri odabiru vozila. Molimo pokusajte ponovo.");
//        }
//    }
}
