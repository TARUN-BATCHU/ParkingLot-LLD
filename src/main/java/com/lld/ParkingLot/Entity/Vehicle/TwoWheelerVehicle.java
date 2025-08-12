package com.lld.ParkingLot.Entity.Vehicle;

import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

public class TwoWheelerVehicle extends Vehicle {

    public TwoWheelerVehicle(String vehicleColor, String vehicleNumber, boolean ev) {
        super(vehicleColor, vehicleNumber, ParkingSpotType.TWO_WHEELER, ev);
    }
}
