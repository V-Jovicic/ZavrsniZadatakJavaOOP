package app;

import models.users.User;
import models.vehicles.Vehicle;
import services.AuthService;

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


        System.out.println("==========");
        System.out.println("Dobro dosli u NSGOClub aplikaciju!");
        System.out.println("1. Prijava");
        System.out.println("2. Registracija");
        System.out.println("0. Izlaz");

        while(true) {
            try {
                System.out.print("Izaberite opciju: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        activeUser = authService.login(users, scanner);
                    }
                    case 2 -> registracija(); // NOT IMPLEMENTED YET
                    case 0 -> { return; }
                    default -> { continue; }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Molimo unesite broj kao opciju!");
            }
        }

    }

}