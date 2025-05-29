package interfaces;

import enums.State;

public interface Serviceable {

    State checkVehicleState();

    void fixVehicle(State state);

}
