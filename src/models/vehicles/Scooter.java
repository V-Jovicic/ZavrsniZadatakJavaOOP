package models.vehicles;

import enums.State;

public class Scooter extends Vehicle {

    double highestSpeed;
    int batterDuration; // described as # of hours of use

    public Scooter(String id, String type, String ownerUsername, int perHourRate, int wheelSize, int maxLoadWeight, State state, boolean isRented, double highestSpeed, int batterDuration) {
        super(id, type, ownerUsername, perHourRate, wheelSize, maxLoadWeight, state, isRented);
        this.highestSpeed = highestSpeed;
        this.batterDuration = batterDuration;
    }

}
