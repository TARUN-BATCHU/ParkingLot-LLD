package com.lld.ParkingLot.Service;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Ticket;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingServiceImpl implements ParkingService {
    
    private TicketService ticketService;
    
    public ParkingServiceImpl() {
        this.ticketService = new TicketServiceImpl();
    }
    
    @Override
    public boolean parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {
        ParkingSpot parkingSpot = findAvailableSpot(parkingLot, vehicle);
        
        if(parkingSpot == null) {
            return false;
        }
        
        // Park the vehicle
        parkingSpot.isAvailable = false;
        parkingSpot.park(vehicle);
        
        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);
        ticket.setParkStartTime(LocalDateTime.now());
        ticket.setFare(0);
        
        int ticketId = 1;
        if(parkingLot.getActiveTickets() != null) {
            ticketId = parkingLot.getActiveTickets().size() + 1;
        }
        ticket.setTicketId(ticketId);
        ticket.setVehicle(vehicle);
        
        // Add ticket to parking lot
        if(parkingLot.getActiveTickets() != null) {
            parkingLot.getActiveTickets().add(ticket);
        } else {
            List<Ticket> activeTickets = new ArrayList<>();
            activeTickets.add(ticket);
            parkingLot.setActiveTickets(activeTickets);
        }
        
        return true;
    }
    
    @Override
    public boolean exitVehicle(String vehicleNumber, ParkingLot parkingLot) {
        Ticket ticket = ticketService.findTicket(vehicleNumber, parkingLot);
        
        if(ticket == null) {
            return false;
        }
        
        // Calculate fare and remove ticket
        ticketService.calculateFare(ticket, parkingLot);
        
        // Free the spot
        ParkingSpot parkingSpot = parkingLot.getParkingSpots().stream()
            .filter(spot -> spot.getVehicle() != null && 
                           spot.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber))
            .findFirst()
            .orElse(null);
            
        if(parkingSpot != null) {
            parkingSpot.isAvailable = true;
            parkingSpot.setVehicle(null);
        }
        
        return true;
    }
    
    @Override
    public ParkingSpot findAvailableSpot(ParkingLot parkingLot, Vehicle vehicle) {
        return parkingLot.findRequiredSpot(parkingLot, vehicle);
    }
}