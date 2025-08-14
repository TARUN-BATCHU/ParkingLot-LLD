package com.lld.ParkingLot;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

import java.util.List;

public interface AdminService {
    ParkingLot createNewParkingLot(String name, List<ParkingSpotData> spotDataList);
    List<ParkingLot> getAllParkingLots();
    ParkingSpotType findParkingSpotType(String spotType);
    
    // Inner class for spot data transfer
    class ParkingSpotData {
        public String spotType;
        public boolean hasCharger;
        public int floorNumber;
        
        public ParkingSpotData(String spotType, boolean hasCharger, int floorNumber) {
            this.spotType = spotType;
            this.hasCharger = hasCharger;
            this.floorNumber = floorNumber;
        }
    }
}
