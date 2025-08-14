package com.lld.ParkingLot.Entity.Parking;

import com.lld.ParkingLot.Entity.Vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParkingSpot {

    public int parkingSpotId;
    public ParkingSpotType parkingSpotType;
    public boolean hasCharger;
    public boolean isAvailable = true;
    public int floorNumber;
    public Vehicle vehicle;

    public ParkingSpot(){}
    public ParkingSpot(int parkingSpotId, ParkingSpotType parkinSpotType, boolean hasCharger, boolean isAvailable, int floorNumber,Vehicle vehicle) {
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotType = parkinSpotType;
        this.hasCharger = hasCharger;
        this.isAvailable = isAvailable;
        this.floorNumber = floorNumber;
        this.vehicle = vehicle;
    }

    public void park(Vehicle vehicle){
        setVehicle(vehicle);
        isAvailable = false;
    }

}
