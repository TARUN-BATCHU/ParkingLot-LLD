package com.lld.ParkingLot.Entity.Vehicle;

import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Vehicle {

    public String vehicleNumber;
    public String vehicleColor;
    public ParkingSpotType requiredSpotType;
    public boolean ev;

    public Vehicle(String vehicleColor, String vehicleNumber, ParkingSpotType requiredSpotType, boolean ev) {
        this.vehicleColor = vehicleColor;
        this.vehicleNumber = vehicleNumber;
        this.requiredSpotType = requiredSpotType;
        this.ev = ev;
    }
}
