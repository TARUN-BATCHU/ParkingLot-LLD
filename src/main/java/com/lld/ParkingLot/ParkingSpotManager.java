package com.lld.ParkingLot;

import com.lld.InputUtil;
import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import com.lld.ParkingLot.Entity.Ticket;
import com.lld.ParkingLot.Entity.Vehicle.FourWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.ThreeWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.TwoWheelerVehicle;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParkingSpotManager{

    public static void park(ParkingLot parkingLot){
        System.out.println("\n🚗 VEHICLE PARKING");
        System.out.println("-".repeat(20));

        Scanner sc = InputUtil.getScanner();

        System.out.print("Enter vehicle number: ");
        String vehicleNumber = sc.nextLine();

        System.out.print("Enter vehicle color: ");
        String vehicleColor = sc.nextLine();

        System.out.print("Is it EV? (true/false): ");
        boolean isEv = sc.nextBoolean();
        sc.nextLine();

        String vehicleType = null;
        while(true) {
            System.out.print("Vehicle Type (2w/3w/4w): ");
            vehicleType = sc.nextLine();

            if(vehicleType.equalsIgnoreCase("2w") ||
                    vehicleType.equalsIgnoreCase("3w") ||
                    vehicleType.equalsIgnoreCase("4w")) {
                break;
            } else {
                System.out.println("❌ Invalid vehicle type! Please enter 2w, 3w, or 4w");
            }
        }

        ParkingSpotType parkingSpotType = findRequiredSpot(vehicleType);
        Vehicle vehicle = null;

        if(parkingSpotType.equals(ParkingSpotType.TWO_WHEELER)){
            vehicle = new TwoWheelerVehicle(vehicleColor,vehicleNumber,isEv);
        }else if(parkingSpotType.equals(ParkingSpotType.THREE_WHEELER)){
            vehicle = new ThreeWheelerVehicle(vehicleColor,vehicleNumber,isEv);
        }else if(parkingSpotType.equals(ParkingSpotType.FOUR_WHEELER)){
            vehicle = new FourWheelerVehicle(vehicleColor,vehicleNumber,isEv);
        }

        ParkingSpot parkingSpot = parkingLot.findRequiredSpot(parkingLot,vehicle);
        if(null != parkingSpot){
            parkingSpot.isAvailable = false;
            parkingSpot.park(vehicle);

            Ticket ticket = new Ticket();
            ticket.setParkingSpot(parkingSpot);
            ticket.setParkStartTime(LocalDateTime.now());
            ticket.setFare(0);

            int ticketId = 1;
            if(parkingLot.getActiveTickets()!=null){
                ticketId = parkingLot.getActiveTickets().size()+1;
            }
            ticket.setTicketId(ticketId);
            ticket.setVehicle(vehicle);

            if(null!=parkingLot.getActiveTickets()) {
                parkingLot.getActiveTickets().add(ticket);
            }else{
                List<Ticket> activeTicket = new ArrayList<>();
                activeTicket.add(ticket);
                parkingLot.setActiveTickets(activeTicket);
            }

            System.out.println("✅ Vehicle parked successfully!");
            System.out.println("📍 Spot ID: " + parkingSpot.parkingSpotId);
            System.out.println("🎫 Ticket ID: " + ticketId);
        }else{
            System.out.println("❌ Sorry! No available parking spots for " + vehicleType.toUpperCase());
        }
    }


    public static void showAvailableSpots(ParkingLot parkingLot, String evSupport, ParkingSpotType parkingSpotType){
        System.out.println("\n📋 AVAILABLE SPOTS");
        System.out.println("-".repeat(20));

        boolean foundSpots = false;

        for(ParkingSpot parkingSpot : parkingLot.getParkingSpots()) {
            if(parkingSpot.isAvailable) {
                boolean shouldShow = false;

                // Case 1: Show spots by vehicle type (options 8, 9, 10)
                if(parkingSpotType != null) {
                    if(parkingSpot.getParkingSpotType().equals(parkingSpotType)) {
                        shouldShow = true;
                    }
                }
                // Case 2: Show EV spots (option 6) or Non-EV spots (option 7)
                else if(evSupport != null) {
                    boolean spotHasCharger = parkingSpot.hasCharger;
                    if(evSupport.equals("true") && spotHasCharger) {
                        shouldShow = true;
                    } else if(evSupport.equals("false") && !spotHasCharger) {
                        shouldShow = true;
                    }
                }
                // Case 3: Show all available spots (option 2)
                else {
                    shouldShow = true;
                }

                if(shouldShow) {
                    System.out.println("ID: " + parkingSpot.parkingSpotId +
                            " | Floor: " + parkingSpot.floorNumber +
                            " | Type: " + parkingSpot.getParkingSpotType() +
                            " | EV Charger: " + (parkingSpot.hasCharger ? "Yes" : "No"));
                    foundSpots = true;
                }
            }
        }

        if(!foundSpots) {
            System.out.println("❌ No available spots found for the selected criteria");
        }
    }

    public static void exitVehicle(ParkingLot parkingLot){
        System.out.println("\n🚗 VEHICLE EXIT");
        System.out.println("-".repeat(15));
        System.out.print("Enter vehicle number: ");
        Scanner sc = InputUtil.getScanner();
        String vehicleNumber = sc.nextLine();
        Ticket ticket = TicketManager.findTicket(vehicleNumber,parkingLot);
        if(ticket==null){
            System.out.println("❌ Ticket was not found for this vehicle");
        }else {
            TicketManager.calculateFare(ticket,parkingLot);
            freeSpot(vehicleNumber, parkingLot);
        }
    }

    public static void freeSpot(String vehicleNumber, ParkingLot parkingLot){
        ParkingSpot parkingSpot = parkingLot.getParkingSpots().stream().filter(spot -> {
            return spot.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber);
        }).findFirst().orElse(null);
        if(parkingSpot!=null){
            parkingSpot.isAvailable = true;
            parkingSpot.vehicle = null;
            System.out.println("Spot vacated successfully");
        }else{
            System.out.println("Spot was not found");
        }
    }

    public static ParkingSpotType findRequiredSpot(String vehicleType){
        if(vehicleType.equalsIgnoreCase("2w")){
            return ParkingSpotType.TWO_WHEELER;
        }else if(vehicleType.equalsIgnoreCase("3w")){
            return ParkingSpotType.THREE_WHEELER;
        }else if(vehicleType.equalsIgnoreCase("4w")){
            return ParkingSpotType.FOUR_WHEELER;
        }else{
            System.out.println("You entered the wrong vehicle type");
            return null;
        }
    }

}
