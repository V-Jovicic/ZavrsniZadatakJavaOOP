package models.users;

public abstract class User {

    protected String username; // usernames are assumed to be unique.
    protected String password;
    protected String name;
    protected String surname;
    protected String type;

    public User(String username, String password, String name, String surname, String type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
