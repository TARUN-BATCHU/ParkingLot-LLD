package com.lld.ParkingLot.Service;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import com.lld.ParkingLot.Entity.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class TicketServiceImpl implements TicketService {
    
    private static final int TWO_WHEELER_PARK_FARE = 10;
    private static final int TWO_WHEELER_EV_FARE = 50;
    private static final int THREE_WHEELER_PARK_FARE = 15;
    private static final int THREE_WHEELER_EV_FARE = 80;
    private static final int FOUR_WHEELER_PARK_FARE = 20;
    private static final int FOUR_WHEELER_EV_FARE = 120;
    
    @Override
    public Ticket findTicket(String vehicleNumber, ParkingLot parkingLot) {
        if(parkingLot.getActiveTickets() == null) {
            return null;
        }
        
        return parkingLot.getActiveTickets().stream()
            .filter(ticket -> ticket.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public void calculateFare(Ticket ticket, ParkingLot parkingLot) {
        ticket.setVacateTime(LocalDateTime.now());
        double duration = Duration.between(ticket.getParkStartTime(), ticket.getVacateTime()).toMinutes();
        
        int parkingCost = switch(ticket.getVehicle().getRequiredSpotType()) {
            case TWO_WHEELER -> TWO_WHEELER_PARK_FARE;
            case THREE_WHEELER -> THREE_WHEELER_PARK_FARE;
            case FOUR_WHEELER -> FOUR_WHEELER_PARK_FARE;
        };
        
        int evChargeCost = 0;
        if(ticket.getVehicle().isEv()) {
            evChargeCost = switch(ticket.getVehicle().getRequiredSpotType()) {
                case TWO_WHEELER -> TWO_WHEELER_EV_FARE;
                case THREE_WHEELER -> THREE_WHEELER_EV_FARE;
                case FOUR_WHEELER -> FOUR_WHEELER_EV_FARE;
            };
        }
        
        int rate = parkingCost + evChargeCost;
        double totalFare = (duration / 60.0) * rate;
        
        System.out.println("Amount to be paid");
        System.out.println("Parking Cost per hour: " + parkingCost + " | EV Charge cost per hour: " + evChargeCost);
        System.out.println("Duration: " + String.format("%.2f", duration/60.0) + " hours");
        System.out.println("TOTAL: $" + String.format("%.2f", totalFare));
        
        ticket.setFare(totalFare);
        System.out.println("PLEASE COLLECT THE AMOUNT");
        
        parkingLot.getActiveTickets().remove(ticket);
    }
}