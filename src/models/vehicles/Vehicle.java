package models.vehicles;

import enums.State;
import interfaces.Rentable;
import interfaces.Serviceable;

public abstract class Vehicle implements Rentable, Serviceable {

    protected String id;
    protected String type;
    protected String ownerUsername;
    protected int perHourRate;
    protected int wheelSize;
    protected int maxLoadWeight;
    protected State state;
    protected boolean isRented;

    public Vehicle(String id, String type, String ownerUsername, int perHourRate, int wheelSize, int maxLoadWeight, State state, boolean isRented) {
        this.id = id;
        this.type = type;
        this.ownerUsername = ownerUsername;
        this.perHourRate = perHourRate;
        this.wheelSize = wheelSize;
        this.maxLoadWeight = maxLoadWeight;
        this.state = state;
        this.isRented = isRented;
    }

    // TODO

    @Override
    public void rentVehicle() {

    }

    @Override
    public void returnVehicle() {

    }

    @Override
    public void checkVehicleState() {

    }

    @Override
    public void fixVehicle() {

    }
}
