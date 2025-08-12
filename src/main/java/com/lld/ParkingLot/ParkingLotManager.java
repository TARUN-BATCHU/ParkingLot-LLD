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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParkingLotManager {

    List<ParkingLot> parkingLots = new ArrayList<>();
    private static ParkingLotManager instance;

    private ParkingLotManager() {
    }

    public static ParkingLotManager getInstance(){
        if(instance == null){
            instance = new ParkingLotManager();
        }
        return instance;
    }

    public List<ParkingLot> showAllParkingLots(){
        return parkingLots;
    }

    public void parkOperations(int parkingLotId){
        ParkingLot parkingLot = parkingLots.get(parkingLotId);
        
        while(true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PARKING LOT: " + parkingLot.parkingLotName);
            System.out.println("=".repeat(50));
            printparkOperations();
            System.out.println("Enter your choice (1-11): ");
            
            Scanner sc = InputUtil.getScanner();
            
            if(!sc.hasNextInt()) {
                System.out.println("❌ Please enter a valid number!");
                sc.nextLine();
                continue;
            }
            
            int operation = sc.nextInt();
            sc.nextLine();
            
            switch(operation){
                case 1 : park(parkingLot); break;
                case 2 : showAvailableSpots(parkingLot,null,null); break;
                case 3 : exitVehicle(parkingLot); break;
                case 4 : addSpot(parkingLot,0); break;
                case 5 : updateSpot(parkingLot); break;
                case 6 : showAvailableSpots(parkingLot,"true",null); break;
                case 7 : showAvailableSpots(parkingLot,"false",null); break;
                case 8 : showAvailableSpots(parkingLot,null,ParkingSpotType.TWO_WHEELER); break;
                case 9 : showAvailableSpots(parkingLot,null,ParkingSpotType.THREE_WHEELER); break;
                case 10 : showAvailableSpots(parkingLot,null,ParkingSpotType.FOUR_WHEELER); break;
                case 11 : 
                    System.out.println("👋 Exiting parking lot operations...");
                    return;
                default: 
                    System.out.println("❌ Invalid choice! Please enter 1-11");
                    continue;
            }
            
            System.out.println("\n" + "-".repeat(30));
            System.out.println("Press Enter to continue...");
            sc.nextLine();
        }
    }

    public void park(ParkingLot parkingLot){
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

    public ParkingSpotType findRequiredSpot(String vehicleType){
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
    public void printparkOperations(){
        System.out.println("1 - park vehicle");
        System.out.println("2 - show available spots");
        System.out.println("3 - exit a vehicle");
        System.out.println("4 - create a new spot");
        System.out.println("5 - update a spot");
        System.out.println("6 - show free ev spots");
        System.out.println("7 - show free non ev spots");
        System.out.println("8 - show free 2 wheeler spots");
        System.out.println("9 - show free 3 wheeler spots");
        System.out.println("10 - show free 4 wheeler spots");
        System.out.println("11 - exit");
    }
    //    public void deleteParkingLot(){
//        Scanner scanner = new Scanner(System.in);
//        int ParkingLotId = scanner.nextInt();
//        ParkingLotManager.deleteParkingLot(ParkingLotId);
//        System.out.println("deleted parking lot");
//        System.exit(0);
//    }
    public void addNewParkingLot(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
    }

    public void showAvailableSpots(ParkingLot parkingLot, String evSupport, ParkingSpotType parkingSpotType){
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

    public void exitVehicle(ParkingLot parkingLot){
        System.out.println("\n🚗 VEHICLE EXIT");
        System.out.println("-".repeat(15));
        System.out.print("Enter vehicle number: ");
        Scanner sc = InputUtil.getScanner();
        String vehicleNumber = sc.nextLine();
        Ticket ticket = findTicket(vehicleNumber,parkingLot);
        if(ticket==null){
            System.out.println("❌ Ticket was not found for this vehicle");
        }else {
            calculateFare(ticket,parkingLot);
            freeSpot(vehicleNumber, parkingLot);
        }
    }

    public Ticket findTicket(String vehicleNumber,ParkingLot parkingLot){
        return parkingLot.getActiveTickets().stream().filter(ticket -> {return ticket.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber);}).findFirst().orElse(null);
    }

    public void calculateFare(Ticket t,ParkingLot parkingLot){
        t.setVacateTime(LocalDateTime.now());
        double duration = Duration.between(t.parkStartTime, t.vacateTime).toMinutes();
        int parkingCost = switch(t.getVehicle().getRequiredSpotType()) {
            case ParkingSpotType.TWO_WHEELER -> 10;
            case ParkingSpotType.THREE_WHEELER -> 15;
            case ParkingSpotType.FOUR_WHEELER -> 20;
        };
        int evChargeCost = 0;
        if(t.getVehicle().isEv()){
            evChargeCost = switch(t.getVehicle().getRequiredSpotType()) {
                case ParkingSpotType.TWO_WHEELER -> 50;
                case ParkingSpotType.THREE_WHEELER -> 80;
                case ParkingSpotType.FOUR_WHEELER -> 120;
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

    public void freeSpot(String vehicleNumber,ParkingLot parkingLot){
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

    public void addSpot(ParkingLot parkingLot,int id){
        System.out.println("GIVE DETAILS TO ADD NEW PARKING SPOT");
        System.out.println("Floor number : ");
        Scanner sc = InputUtil.getScanner();
        int floor = sc.nextInt();
        System.out.println("does the spot contains charger");
        boolean hasCharger = sc.nextBoolean();
        sc.nextLine();
        System.out.println("parking spot type (2w,3w,4w");
        String spotType = sc.nextLine();
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setVehicle(null);
        parkingSpot.setAvailable(true);
        parkingSpot.setFloorNumber(floor);
        parkingSpot.setHasCharger(hasCharger);
        ParkingSpotType parkingSpotType = null;
        if(spotType.equalsIgnoreCase("2W")){
            parkingSpotType = ParkingSpotType.TWO_WHEELER;
        }else if(spotType.equalsIgnoreCase("3W")){
            parkingSpotType = ParkingSpotType.THREE_WHEELER;
        }else if(spotType.equalsIgnoreCase("4W")){
            parkingSpotType = ParkingSpotType.FOUR_WHEELER;
        }else{
            System.out.println("You entered the wrong spot type choose 2w,3w,4w");
            throw new IllegalArgumentException();
        }
        parkingSpot.setParkingSpotType(parkingSpotType);
        if(id==0) {
            parkingSpot.setParkingSpotId(parkingLot.getParkingSpots().size() + 1);
        }else{
            parkingSpot.setParkingSpotId(id);
        }
        parkingLot.getParkingSpots().add(parkingSpot);
        System.out.println("New spot added successfully");
    }

    public void updateSpot(ParkingLot parkingLot){
        showAllParkingSpots(parkingLot);
        Scanner sc = InputUtil.getScanner();
        System.out.println("Select which spot you want to update");
        int spot = sc.nextInt();
        updateParkingSpot(spot,parkingLot);
    }

    public void showAllParkingSpots(ParkingLot parkingLot){
        parkingLot.getParkingSpots().stream().forEach(spot -> {System.out.println(spot.parkingSpotId+" :"+spot.parkingSpotType+" floor : "+spot.floorNumber+" ev : "+spot.hasCharger);});
    }

    public void updateParkingSpot(int id,ParkingLot parkingLot){
        System.out.println("Give the new details about the spot "+id);
        ParkingSpot parkingSpot = parkingLot.getParkingSpots().get(id);
        parkingLot.getParkingSpots().remove(parkingSpot);
        addSpot(parkingLot,id);
    }

}
