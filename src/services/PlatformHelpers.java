package services;

import models.cards.Card;
import models.users.Renter;
import models.users.User;

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
            Card card;
            if (activeUser.getType().equalsIgnoreCase("renter")) {
                card = ((Renter) activeUser).getCardId().findcard(); // TODO
                System.out.println("2. Deponovanje sredstava na racun");
                System.out.println("3. Iznajmljivanje vozila");
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
                    case 1 -> pretragaVozila(); // TODO
                    case 2 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            depositBalance(card);
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            pregledVozila(); // TODO
                        }
                    }
                    case 3 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            iznajmljivanjeVozila(); // TODO
                        } else if (activeUser.getType().equalsIgnoreCase("serviceman")) {
                            popravkaVozila(); // TODO
                        }
                    }
                    case 4 -> {
                        if (activeUser.getType().equalsIgnoreCase("renter")) {
                            if (!card.getCurrentlyRentedVehicleId().equalsIgnoreCase("")) {
                                iznajmljivanjeVozila(); // TODO
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
}
