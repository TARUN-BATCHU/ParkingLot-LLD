package com.lld.ParkingLot.Service;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Ticket;

public interface TicketService {
    Ticket findTicket(String vehicleNumber, ParkingLot parkingLot);
    void calculateFare(Ticket ticket, ParkingLot parkingLot);
}