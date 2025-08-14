package com.lld.ParkingLot.Service;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;

public interface ParkingService {
    boolean parkVehicle(Vehicle vehicle, ParkingLot parkingLot);
    boolean exitVehicle(String vehicleNumber, ParkingLot parkingLot);
    ParkingSpot findAvailableSpot(ParkingLot parkingLot, Vehicle vehicle);
}