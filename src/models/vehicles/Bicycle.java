package models.vehicles;

import enums.State;

public class Bicycle extends Vehicle {

    private int numOfGears;
    private double height;

    public Bicycle(String id, String type, String ownerUsername, int perHourRate, int wheelSize, int maxLoadWeight, State state, boolean isRented, int numOfGears, double height) {
        super(id, type, ownerUsername, perHourRate, wheelSize, maxLoadWeight, state, isRented);
        this.numOfGears = numOfGears;
        this.height = height;
    }

    public int getNumOfGears() {
        return numOfGears;
    }

    public void setNumOfGears(int numOfGears) {
        this.numOfGears = numOfGears;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
