package com.lld.ParkingLot.Entity;

import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Ticket {

    public int ticketId;
    public LocalDateTime parkStartTime;
    public LocalDateTime vacateTime;
    public double fare;
    public ParkingSpot parkingSpot;
    public Vehicle vehicle;
}
