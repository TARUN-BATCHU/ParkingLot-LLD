package com.lld.ParkingLot.Factory;

import com.lld.ParkingLot.Entity.Vehicle.FourWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.ThreeWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.TwoWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;

public class VehicleFactory {
    
    public static Vehicle createVehicle(String vehicleType, String vehicleColor, String vehicleNumber, boolean isEv) {
        return switch(vehicleType.toLowerCase()) {
            case "2w" -> new TwoWheelerVehicle(vehicleColor, vehicleNumber, isEv);
            case "3w" -> new ThreeWheelerVehicle(vehicleColor, vehicleNumber, isEv);
            case "4w" -> new FourWheelerVehicle(vehicleColor, vehicleNumber, isEv);
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        };
    }
    
    public static boolean isValidVehicleType(String vehicleType) {
        return vehicleType.equalsIgnoreCase("2w") || 
               vehicleType.equalsIgnoreCase("3w") || 
               vehicleType.equalsIgnoreCase("4w");
    }
}