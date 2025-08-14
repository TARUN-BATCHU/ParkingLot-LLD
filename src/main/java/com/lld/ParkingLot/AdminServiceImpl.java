package com.lld.ParkingLot;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import com.lld.ParkingLot.Entity.ParkingLotManager;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    
    @Override
    public ParkingLot createNewParkingLot(String name, List<ParkingSpotData> spotDataList) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        
        for(int i = 0; i < spotDataList.size(); i++) {
            ParkingSpotData data = spotDataList.get(i);
            ParkingSpotType parkingSpotType = findParkingSpotType(data.spotType);
            
            if(parkingSpotType == null) {
                throw new IllegalArgumentException("Invalid spot type: " + data.spotType);
            }
            
            int spotId = i + 1;
            ParkingSpot parkingSpot = new ParkingSpot(spotId, parkingSpotType, data.hasCharger, true, data.floorNumber, null);
            parkingSpots.add(parkingSpot);
        }
        
        ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
        int parkingLotId = 1;
        if(parkingLotManager.getParkingLots() != null) {
            parkingLotId = parkingLotManager.getParkingLots().size() + 1;
        }
        
        ParkingLot parkingLot = new ParkingLot(parkingLotId, name, parkingSpots, null);
        parkingLotManager.addNewParkingLot(parkingLot);
        
        return parkingLot;
    }
    
    @Override
    public List<ParkingLot> getAllParkingLots() {
        return ParkingLotManager.getInstance().showAllParkingLots();
    }
    
    @Override
    public ParkingSpotType findParkingSpotType(String spotType) {
        if(spotType.equalsIgnoreCase("2w")) {
            return ParkingSpotType.TWO_WHEELER;
        } else if(spotType.equalsIgnoreCase("3w")) {
            return ParkingSpotType.THREE_WHEELER;
        } else if(spotType.equalsIgnoreCase("4w")) {
            return ParkingSpotType.FOUR_WHEELER;
        } else {
            return null;
        }
    }
}