package com.lld.ParkingLot.Entity.Vehicle;

import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

public class FourWheelerVehicle extends Vehicle {

    public FourWheelerVehicle(String vehicleColor, String vehicleNumber, boolean ev) {
        super(vehicleColor, vehicleNumber, ParkingSpotType.FOUR_WHEELER, ev);
    }
}
