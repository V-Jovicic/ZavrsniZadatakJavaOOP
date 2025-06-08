package models.users;

public class UnknownUser extends User {

    // When a user type cannot be read, they will be instantiated as this class.
    public UnknownUser(String userName, String password, String name, String surname, String type) {
        super(userName, password, name, surname, type);
    }

}
