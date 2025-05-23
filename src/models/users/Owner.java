package models.users;

public class Owner extends User {

    private String arrOfOwnedVehicles;  // Vehicles aren't stored as objects in each Owner object, but rather fetched as needed by their ID.
                                        // The ID format in the db is "VXXXX_VXXXX" (X representing integer numbers, underscores acting as separators if multiple vehicles).
                                        // This bypasses issues during the initial loads where a user may be loaded before their respective vehicle object(s)
                                        // and also reduces time complexity of the initial loads, as we don't need to assign whole objects.

    public Owner(String userName, String password, String name, String surname, String type, String arrOfOwnedVehicles) {
        super(userName, password, name, surname, type);
        this.arrOfOwnedVehicles = arrOfOwnedVehicles;
    }

    public String getArrOfOwnedVehicles() {
        return arrOfOwnedVehicles;
    }
    public void setArrOfOwnedVehicles(String arrOfOwnedVehicles) {
        this.arrOfOwnedVehicles = arrOfOwnedVehicles;
    }

}
