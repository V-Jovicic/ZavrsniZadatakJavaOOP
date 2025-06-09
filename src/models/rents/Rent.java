package models.rents;

import enums.AdditionalEquipment;

import java.time.LocalDateTime;
import java.util.List;

public class Rent {

    private String id;
    private LocalDateTime dateTimeValidFrom;
    private LocalDateTime dateTimeValidUntil;
    private String renterUsername;
    private String vehicleId;
    private List<AdditionalEquipment> equipment;
    private boolean serviceDone;

    public Rent(String id, LocalDateTime dateTimeValidFrom, LocalDateTime dateTimeValidUntil, String renterUsername, String vehicleId, List<AdditionalEquipment> equipment, boolean serviceDone) {
        this.id = id;
        this.dateTimeValidFrom = dateTimeValidFrom;
        this.dateTimeValidUntil = dateTimeValidUntil;
        this.renterUsername = renterUsername;
        this.vehicleId = vehicleId;
        this.equipment = equipment;
        this.serviceDone = serviceDone;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTimeValidFrom() {
        return dateTimeValidFrom;
    }
    public void setDateTimeValidFrom(LocalDateTime dateTimeValidFrom) {
        this.dateTimeValidFrom = dateTimeValidFrom;
    }

    public LocalDateTime getDateTimeValidUntil() {
        return dateTimeValidUntil;
    }
    public void setDateTimeValidUntil(LocalDateTime dateTimeValidUntil) {
        this.dateTimeValidUntil = dateTimeValidUntil;
    }

    public String getRenterUsername() {
        return renterUsername;
    }
    public void setRenterUsername(String renterUsername) {
        this.renterUsername = renterUsername;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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
                ", dateTimeValidFrom=" + dateTimeValidFrom +
                ", dateTimeValidUntil=" + dateTimeValidUntil +
                ", renterUsername='" + renterUsername + '\'' +
                ", vehicleId=" + vehicleId +
                ", equipment=" + equipment +
                ", serviceDone=" + serviceDone +
                '}';
    }

}
