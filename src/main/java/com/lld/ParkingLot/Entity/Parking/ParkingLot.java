package com.lld.ParkingLot.Entity.Parking;

import com.lld.ParkingLot.Entity.Ticket;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Getter
@Setter
public class ParkingLot {

    public int parkingLotId;
    public String parkingLotName;
    public List<ParkingSpot> parkingSpots = new ArrayList<>();
    public List<Ticket> activeTickets;


    public ParkingLot(int parkingLotId, String parkingLotName, List<ParkingSpot> parkingSpots, List<Ticket> activeTickets) {
        this.parkingLotId = parkingLotId;
        this.parkingLotName = parkingLotName;
        this.parkingSpots = parkingSpots;
        this.activeTickets = activeTickets;
    }

    public ParkingSpot findRequiredSpot(ParkingLot parkingLot,Vehicle vehicle){
        List<ParkingSpot> spots = parkingLot.getParkingSpots();
        for(int i=0; i<spots.size(); i++){
            if(spots.get(i).isAvailable && spots.get(i).getParkingSpotType().equals(vehicle.getRequiredSpotType())){
                return spots.get(i);
            }
        }
        return null;
    }
}
