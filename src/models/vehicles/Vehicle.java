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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public int getPerHourRate() {
        return perHourRate;
    }

    public void setPerHourRate(int perHourRate) {
        this.perHourRate = perHourRate;
    }

    public int getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(int wheelSize) {
        this.wheelSize = wheelSize;
    }

    public int getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public void setMaxLoadWeight(int maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public State checkVehicleState() {
        return state;
    }

    @Override
    public void fixVehicle(State state) {
        this.state = state;
    }

    // TODO
    @Override
    public void rentVehicle() {

    }

    @Override
    public void returnVehicle() {

    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", perHourRate=" + perHourRate +
                ", wheelSize=" + wheelSize +
                ", maxLoadWeight=" + maxLoadWeight +
                ", state=" + state +
                ", isRented=" + isRented +
                '}';
    }

}
