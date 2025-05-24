package app;

import models.users.User;
import models.vehicles.Vehicle;
import services.AuthService;
import util.PlatformHelpers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Platform {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();
        List<User> users = new ArrayList<User>();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        User activeUser;

        while(true) {
            System.out.println("===============");
            System.out.println("Dobro dosli u NSGOClub aplikaciju!");
            System.out.println("1. Prijava");
            System.out.println("2. Registracija");
            System.out.println("0. Izlaz");

            try {
                System.out.print("Izaberite opciju: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        activeUser = authService.login(users, scanner); // THIS CAN RETURN NULL
                        // We check for null as this is the return value when a user enters incorrect credentials.
                        if (activeUser != null) {
                            PlatformHelpers.userLoggedInMenu(activeUser);
                        }
                    }
                    case 2 -> {
                        activeUser = authService.registration(scanner); // THIS CAN RETURN NULL
                        // We check for null as this is the return value when a user wishes to return without creating an account.
                        if (activeUser != null) {
                            users.add(activeUser);
                            PlatformHelpers.userLoggedInMenu(activeUser);
                        }
                    }
                    case 0 -> { return; }
                    default -> { continue; }
                }
            } catch (InputMismatchException e) {
                System.out.println("Molimo unesite broj kao opciju!");
            }
        }

    }

}