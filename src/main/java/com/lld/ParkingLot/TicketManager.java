package com.lld.ParkingLot;

import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import com.lld.ParkingLot.Entity.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class TicketManager {

    public static final int TWO_WHEELER_PARK_FARE = 10;
    public static final int TWO_WHEELER_EV_FARE = 50;
    public static final int THREE_WHEELER_PARK_FARE = 15;
    public static final int THREE_WHEELER_EV_FARE = 80;
    public static final int FOUR_WHEELER_PARK_FARE = 20;
    public static final int FOUR_WHEELER_EV_FARE = 120;

    public static Ticket findTicket(String vehicleNumber, ParkingLot parkingLot){
        return parkingLot.getActiveTickets().stream().filter(ticket -> {return ticket.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber);}).findFirst().orElse(null);
    }

    public static void calculateFare(Ticket t, ParkingLot parkingLot){
        t.setVacateTime(LocalDateTime.now());
        double duration = Duration.between(t.parkStartTime, t.vacateTime).toMinutes();
        int parkingCost = switch(t.getVehicle().getRequiredSpotType()) {
            case ParkingSpotType.TWO_WHEELER -> TWO_WHEELER_PARK_FARE;
            case ParkingSpotType.THREE_WHEELER -> THREE_WHEELER_PARK_FARE;
            case ParkingSpotType.FOUR_WHEELER -> FOUR_WHEELER_PARK_FARE;
        };
        int evChargeCost = 0;
        if(t.getVehicle().isEv()){
            evChargeCost = switch(t.getVehicle().getRequiredSpotType()) {
                case ParkingSpotType.TWO_WHEELER -> TWO_WHEELER_EV_FARE;
                case ParkingSpotType.THREE_WHEELER -> THREE_WHEELER_EV_FARE;
                case ParkingSpotType.FOUR_WHEELER -> FOUR_WHEELER_EV_FARE;
            };
        }
        int rate = parkingCost + evChargeCost;
        System.out.println("Amount to be paid");
        System.out.println("Parking Cost per hour : "+parkingCost+" charge cost per hour : "+evChargeCost);
        System.out.println("Parking Cost per total : "+parkingCost*duration+" charge cost per hour : "+evChargeCost*duration);
        System.out.println("TOTAL : "+(duration / 60.0)*rate);
        t.setFare((duration / 60.0)*rate);
        System.out.println("PLEASE COLLECT THE AMOUNT");
        parkingLot.getActiveTickets().remove(t);
    }
}
