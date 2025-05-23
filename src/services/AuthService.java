package services;

import models.users.User;
import util.PlatformHelpers;

import java.util.List;
import java.util.Scanner;

public class AuthService {

    public User login (List<User> userArray, Scanner scanner) {

        // The function checks for a valid userName + password combination.
        // If match is found, print a success message and call the logged in display function to proceed
        // If match not found, print fail message and exit

        System.out.println("===============");
        System.out.print("Unesite korisničko ime: ");
        String username = scanner.nextLine();
        System.out.print("Unesite lozinku: ");
        String password = scanner.nextLine();

        for (User user : userArray) {
            if (username.equals(user.getUserName()) && password.equals(user.getPassword())) {
                System.out.println("Uspešno ste se prijavili kao: " + user.getClass().getSimpleName());
                PlatformHelpers.userLoggedInDisplay(user);
                return user;
            }
        }

        System.out.println("Neispravni podaci. Pokušajte ponovo.");
        return null;
    }

}
