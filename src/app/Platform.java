package app;

import models.users.User;
import services.AuthService;
import services.DatabaseService;
import services.PlatformHelpers;

import java.util.List;
import java.util.Scanner;

public class Platform {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();
        DatabaseService dbService = new DatabaseService();
        PlatformHelpers platformHelpers = new PlatformHelpers(dbService, scanner);
        User activeUser;

        while(true) {
            System.out.println("===============");
            System.out.println("Dobro dosli u NSGOClub aplikaciju!");
            System.out.println("1. Prijava");
            System.out.println("2. Registracija");
            System.out.println("0. Izlaz");

            try {
                System.out.print("Izaberite opciju: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        activeUser = authService.login(dbService.getUsersArr(), scanner); // THIS CAN RETURN NULL
                        // We check for null as this is the return value when a user enters incorrect credentials.
                        if (activeUser != null) {
                            platformHelpers.userLoggedInMenu(activeUser);
                        }
                    }
                    case 2 -> {
                        activeUser = authService.registration(dbService, scanner); // THIS CAN RETURN NULL
                        // We check for null as this is the return value when a user wishes to return without creating an account.
                        if (activeUser != null) {
                            // Fetch entire list of users, append the new user, set the new list
                            List<User> newArr = dbService.getUsersArr();
                            newArr.add(activeUser);
                            dbService.setUsersArr(newArr);
                            platformHelpers.userLoggedInMenu(activeUser);
                        }
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Nepostojeca opcija!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Molimo unesite broj kao opciju!");
            }
        }

    }

}