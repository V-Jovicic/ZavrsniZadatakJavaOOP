package models.vehicles;

import enums.State;

public class Scooter extends Vehicle {

    double highestSpeed;
    int batteryDuration; // described as # of hours of use

    public Scooter(String id, String type, String ownerUsername, int perHourRate, int wheelSize, int maxLoadWeight, State state, boolean isRented, double highestSpeed, int batteryDuration) {
        super(id, type, ownerUsername, perHourRate, wheelSize, maxLoadWeight, state, isRented);
        this.highestSpeed = highestSpeed;
        this.batteryDuration = batteryDuration;
    }

    public double getHighestSpeed() {
        return highestSpeed;
    }

    public void setHighestSpeed(double highestSpeed) {
        this.highestSpeed = highestSpeed;
    }

    public int getBatteryDuration() {
        return batteryDuration;
    }

    public void setBatteryDuration(int batteryDuration) {
        this.batteryDuration = batteryDuration;
    }
}
