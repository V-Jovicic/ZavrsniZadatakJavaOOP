package util;

import enums.State;
import models.rents.Rent;
import models.users.User;
import models.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchUtils {

    public static boolean usernameAlreadyExists(List<User> usersArr, String username) {
        for (User targetUser : usersArr) {
            if (username.equalsIgnoreCase(targetUser.getUsername())) return true;
        }
        return false;
    }

    public static List<Vehicle> vehicleSearch(Scanner scanner, List<Vehicle> vehiclesArr, List<Rent> rentsArr, boolean availabilitySearchNeeded) {
        String[] returnArrParams = {"null", "null", "null"};
        List<Vehicle> returnArr;

        while (true) {
            System.out.println("===============");
            System.out.println("PRETRAGA VOZILA");
            System.out.println("1. Tip");
            System.out.println("2. Servisiranost");
            if (availabilitySearchNeeded) {
                System.out.println("3. Zauzetost");
            }
            System.out.println("9. Pretrazi");
            System.out.println("0. Povratak nazad");
            System.out.println("===============");
            System.out.print("Izaberite parametar za pretragu vozila: ");

            try {
                int mainChoice = Integer.parseInt(scanner.nextLine());
                switch (mainChoice) {
                    case 1 -> {
                        System.out.println("===============");
                        System.out.println("1. Bicikl");
                        System.out.println("2. Trotinet");
                        System.out.println("0. Povratak nazad");
                        System.out.print("Izaberite koji tip vozila zelite da trazite: ");
                        try {
                            int case1Choice = Integer.parseInt(scanner.nextLine());
                            switch (case1Choice) {
                                case 1 -> returnArrParams[0] = "bicycle";
                                case 2 -> returnArrParams[0] = "scooter";
                                case 0 -> {
                                }
                                default -> System.out.println("Nepostojeci izbor!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("===============");
                            System.out.println("Molimo unesite broj!");
                        }
                    }
                    case 2 -> {
                        System.out.println("===============");
                        System.out.println("1. Uradjen servis");
                        System.out.println("2. Neuradjen servis");
                        System.out.println("0. Povratak nazad");
                        System.out.print("Izaberite koji tip vozila zelite da trazite: ");
                        try {
                            int case3Choice = Integer.parseInt(scanner.nextLine());
                            switch (case3Choice) {
                                case 1 -> returnArrParams[2] = "true";
                                case 2 -> returnArrParams[2] = "false";
                                case 0 -> {
                                }
                                default -> System.out.println("Nepostojeci izbor!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("===============");
                            System.out.println("Molimo unesite broj!");
                        }
                    }
                    case 3 -> {
                        if (!availabilitySearchNeeded) {
                            System.out.println("===============");
                            System.out.println("Nepostojeci izbor!");
                            continue;
                        }
                        System.out.println("===============");
                        System.out.println("1. Zauzeto");
                        System.out.println("2. Slobodno");
                        System.out.println("0. Povratak nazad");
                        System.out.println("===============");
                        System.out.print("Izaberite koji tip vozila zelite da trazite: ");
                        try {
                            int case2Choice = Integer.parseInt(scanner.nextLine());
                            switch (case2Choice) {
                                case 1 -> returnArrParams[1] = "true";
                                case 2 -> returnArrParams[1] = "false";
                                case 0 -> {
                                }
                                default -> {
                                    System.out.println("===============");
                                    System.out.println("Nepostojeci izbor!");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("===============");
                            System.out.println("Molimo unesite broj!");
                        }
                    }
                    case 9 -> {
                        List<Vehicle> arr1;
                        List<Vehicle> arr2;
                        if (returnArrParams[0].equals("null")) {
                            arr1 = vehiclesArr;
                        } else {
                            arr1 = vehicleSearchByType(vehiclesArr, returnArrParams[0]);
                        }
                        if (returnArrParams[1].equals("null")) {
                            arr2 = arr1;
                        } else {
                            arr2 = vehicleSearchByService(arr1, rentsArr, Boolean.parseBoolean(returnArrParams[2]));
                        }
                        if (!availabilitySearchNeeded) {
                            returnArr = vehicleSearchByAvailability(arr2, false);
                        } else if (returnArrParams[2].equals("null")) {
                            returnArr = arr2;
                        } else {
                            returnArr = vehicleSearchByAvailability(arr2, Boolean.parseBoolean(returnArrParams[1]));
                        }
                        return returnArr;
                    }
                    case 0 -> {
                        return null;
                    }
                    default -> System.out.println("Nepostojeci izbor!");
                }
            } catch (NumberFormatException e) {
                System.out.println("===============");
                System.out.println("Molimo unesite broj!");
            }
        }
    }

    public static List<Vehicle> vehicleSearchByType(List<Vehicle> vehiclesArr, String targetType) {
        List<Vehicle> returnArr = new ArrayList<>();
        for (Vehicle vehicle : vehiclesArr) {
            if (vehicle.getType().equalsIgnoreCase(targetType)) {
                returnArr.add(vehicle);
            }
        }
        return returnArr;
    }

    public static List<Vehicle> vehicleSearchByAvailability(List<Vehicle> vehiclesArr, boolean isRented) {
        List<Vehicle> returnArr = new ArrayList<>();
        for (Vehicle vehicle : vehiclesArr) {
            if (vehicle.isRented() == isRented) {
                returnArr.add(vehicle);
            }
        }
        return returnArr;
    }

    public static List<Vehicle> vehicleSearchByService(List<Vehicle> vehiclesArr, List<Rent> rentsArr, boolean serviceDone) {
        List<Vehicle> returnArr = new ArrayList<>();
        for (Rent rent : rentsArr) {
            if (rent.isServiceDone() == serviceDone && rent.getDateTimeRentedUntil() != null) {
                for (Vehicle vozilo : vehiclesArr) {
                    if (rent.getRentedVehicleId().equalsIgnoreCase(vozilo.getId())) {
                        returnArr.add(vozilo);
                    }
                }
            }
        }
        return returnArr;
    }

    public static List<Vehicle> vehicleSearchByState(List<Vehicle> vehiclesArr, State[] targetStates) {
        List<Vehicle> returnArr = new ArrayList<>();
        for (Vehicle vehicle : vehiclesArr) {
            for (State state : targetStates) {
                if (vehicle.checkVehicleState() == state) {
                    returnArr.add(vehicle);
                }
            }
        }
        return returnArr;
    }
}
