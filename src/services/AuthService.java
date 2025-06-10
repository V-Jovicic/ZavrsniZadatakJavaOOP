package services;

import models.cards.Card;
import models.users.Renter;
import models.users.Serviceman;
import models.users.User;
import util.SearchUtils;

import java.util.List;
import java.util.Scanner;

public class AuthService {

    public User login (List<User> userArray, Scanner scanner) {

        // The function checks for a valid userName + password combination.
        // If match is found, print a success message and returns the user.
        // If match not found, print fail message and returns null. This is later checked in Platform.java.

        System.out.println("===============");
        System.out.print("Unesite korisničko ime: ");
        String username = scanner.nextLine();
        System.out.print("Unesite lozinku: ");
        String password = scanner.nextLine();

        for (User user : userArray) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                System.out.println("Uspešno ste se prijavili kao: " + user.getClass().getSimpleName());
                return user;
            }
        }

        System.out.println("Neispravni podaci.");
        return null;
    }

    public User registration (DatabaseService dbService, Scanner scanner) {

        // We create a blank user, and depending on the type generate and assign a card ID.
        // If registration is successful, this user is then returned.
        // If registration is not successful, null is returned. This is later checked in Platform.java.

        User newUser;

        while (true) {
            System.out.println("===============");
            System.out.println("Vrsta naloga:");
            System.out.println("1. Iznajmljivač");
            System.out.println("2. Serviser");
            System.out.println("0. Povratak nazad");
            System.out.print("Izaberite tip korisnika za registraciju: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                // If the choice isn't a valid option we break the current iteration of the while loop.
                if (choice != 1 && choice != 2 && choice != 0) {
                    System.out.println("Nepostojeca opcija!");
                    continue;
                }
            } catch (NumberFormatException e) {
                // If the choice isn't a number we break the current iteration of the while loop.
                System.out.println("Molimo unesite broj kao opciju!");
                continue;
            }

            // If the choice is 0, there is no need to continue with the method.
            // We return null immediately. This return is checked for null in Platform.java before proceeding.
            if (choice == 0) {
                return null;
            }else {
                System.out.print("Unesite korisničko ime: ");
                String username = scanner.nextLine();
                // We check whether the user already exists. It is assumed that usernames are unique.
                boolean check = SearchUtils.usernameAlreadyExists(dbService.getUsersArr(), username);
                if (check) {
                    System.out.println("This username already exists, please choose a different one!");
                    continue;
                }
                System.out.print("Unesite lozinku: ");
                String password = scanner.nextLine();
                System.out.print("Unesite ime: ");
                String name = scanner.nextLine();
                System.out.print("Unesite prezime: ");
                String surname = scanner.nextLine();

                // Despite int choice having only 2 possible values at this point in the code, for consistency sakes I used a switch.
                // This ensures intended behaviour in case of any missed possibilities, and enables scalability in regard to # of user types.
                switch (choice) {
                    case 1 -> {
                        // We generate a user's card and add it to the database
                        Card newUserCard = dbService.generateCard(username);
                        List<Card> newList = dbService.getCardsArr();
                        newList.add(newUserCard);
                        dbService.setCardsArr(newList);
                        // Creating a user to return
                        newUser = new Renter(username, password, name, surname, "renter", newUserCard.getId());
                        return newUser;
                    }
                    case 2 -> {
                        newUser = new Serviceman(username, password, name, surname, "serviceman");
                        return newUser;
                    }
                    default -> {
                        // For consistency, we consider a default case and return null.
                        // This return is checked for null in Platform.java before proceeding.
                        System.out.println("Greska pri kreaciji korisnika. Nepostojeci tip.");
                        return null;
                    }
                }
            }
        }
    }

}
