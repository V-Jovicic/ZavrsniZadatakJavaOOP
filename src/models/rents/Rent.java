package models.rents;

import enums.AdditionalEquipment;

import java.time.LocalDateTime;
import java.util.List;

public class Rent {

    private String id;
    private LocalDateTime dateTimeRentedFrom;
    private LocalDateTime dateTimeRentedUntil;
    private String renterUsername;
    private String rentedVehicleId;
    private List<AdditionalEquipment> equipment;
    private boolean serviceDone;

    public Rent(String id, LocalDateTime dateTimeRentedFrom, LocalDateTime dateTimeRentedUntil, String renterUsername, String rentedVehicleId, List<AdditionalEquipment> equipment, boolean serviceDone) {
        this.id = id;
        this.dateTimeRentedFrom = dateTimeRentedFrom;
        this.dateTimeRentedUntil = dateTimeRentedUntil;
        this.renterUsername = renterUsername;
        this.rentedVehicleId = rentedVehicleId;
        this.equipment = equipment;
        this.serviceDone = serviceDone;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTimeRentedFrom() {
        return dateTimeRentedFrom;
    }

    public void setDateTimeRentedFrom(LocalDateTime dateTimeRentedFrom) {
        this.dateTimeRentedFrom = dateTimeRentedFrom;
    }

    public LocalDateTime getDateTimeRentedUntil() {
        return dateTimeRentedUntil;
    }

    public void setDateTimeRentedUntil(LocalDateTime dateTimeRentedUntil) {
        this.dateTimeRentedUntil = dateTimeRentedUntil;
    }

    public String getRenterUsername() {
        return renterUsername;
    }
    public void setRenterUsername(String renterUsername) {
        this.renterUsername = renterUsername;
    }

    public String getRentedVehicleId() {
        return rentedVehicleId;
    }

    public void setRentedVehicleId(String rentedVehicleId) {
        this.rentedVehicleId = rentedVehicleId;
    }

    public List<AdditionalEquipment> getEquipment() {
        return equipment;
    }
    public void setEquipment(List<AdditionalEquipment> equipment) {
        this.equipment = equipment;
    }

    public boolean isServiceDone() {
        return serviceDone;
    }
    public void setServiceDone(boolean serviceDone) {
        this.serviceDone = serviceDone;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id='" + id + '\'' +
                ", dateTimeValidFrom=" + dateTimeRentedFrom +
                ", dateTimeValidUntil=" + dateTimeRentedUntil +
                ", renterUsername='" + renterUsername + '\'' +
                ", vehicleId=" + rentedVehicleId +
                ", equipment=" + equipment +
                ", serviceDone=" + serviceDone +
                '}';
    }

}
