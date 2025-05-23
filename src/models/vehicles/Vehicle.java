package models.vehicles;

import models.users.User;

public abstract class Vehicle {

    protected String id;
    protected String type;
    protected User vlasnik;
    protected int perHourRate;
    protected int wheelSize;
    protected int maxLoadWeight;
    protected boolean isRented;
}
