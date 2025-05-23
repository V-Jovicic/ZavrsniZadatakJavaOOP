package models.users;

public class Renter extends User {

    private String cardId;  // Cards aren't stored as objects in each Renter object, but rather fetched as needed by their ID.
                            // The ID format in the db is "CXXXX" (X representing integer numbers).
                            // This bypasses issues during the initial loads where a user may be loaded before their respective card object
                            // and also reduces time complexity of the initial loads, as we don't need to assign whole objects.

    public Renter(String userName, String password, String name, String surname, String type, String cardId) {
        super(userName, password, name, surname, type);
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
