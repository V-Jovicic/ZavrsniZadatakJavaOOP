package models.users;

public abstract class User {
    protected String userName;
    protected String password;
    protected String name;
    protected String surname;
    protected String type;

    public User(String userName, String password, String name, String surname, String type) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
}
