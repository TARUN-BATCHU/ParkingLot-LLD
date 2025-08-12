package com.lld.ParkingLot.Entity.Vehicle;

import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

public class ThreeWheelerVehicle extends Vehicle {

    public ThreeWheelerVehicle(String vehicleColor, String vehicleNumber, boolean ev) {
        super(vehicleColor, vehicleNumber, ParkingSpotType.THREE_WHEELER, ev);
    }
}
