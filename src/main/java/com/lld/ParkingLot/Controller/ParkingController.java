package com.lld.ParkingLot.Controller;

import com.lld.InputUtil;
import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import com.lld.ParkingLot.Entity.Vehicle.Vehicle;
import com.lld.ParkingLot.Factory.VehicleFactory;
import com.lld.ParkingLot.Service.ParkingService;
import com.lld.ParkingLot.Service.ParkingServiceImpl;

import java.util.Scanner;

public class ParkingController {
    
    private ParkingService parkingService;
    
    public ParkingController() {
        this.parkingService = new ParkingServiceImpl();
    }
    
    public void parkOperations(ParkingLot parkingLot) {
        while(true) {
            System.out.println("\\n" + "=".repeat(50));
            System.out.println("PARKING LOT: " + parkingLot.parkingLotName);
            System.out.println("=".repeat(50));
            printParkOperations();
            System.out.println("Enter your choice (1-11): ");
            
            Scanner sc = InputUtil.getScanner();
            
            if(!sc.hasNextInt()) {
                System.out.println("❌ Please enter a valid number!");
                sc.nextLine();
                continue;
            }
            
            int operation = sc.nextInt();
            sc.nextLine();
            
            switch(operation) {
                case 1 : parkVehicle(parkingLot); break;
                case 2 : showAvailableSpots(parkingLot, null, null); break;
                case 3 : exitVehicle(parkingLot); break;
                case 4 : addSpot(parkingLot); break;
                case 5 : updateSpot(parkingLot); break;
                case 6 : showAvailableSpots(parkingLot, "true", null); break;
                case 7 : showAvailableSpots(parkingLot, "false", null); break;
                case 8 : showAvailableSpots(parkingLot, null, ParkingSpotType.TWO_WHEELER); break;
                case 9 : showAvailableSpots(parkingLot, null, ParkingSpotType.THREE_WHEELER); break;
                case 10 : showAvailableSpots(parkingLot, null, ParkingSpotType.FOUR_WHEELER); break;
                case 11 : 
                    System.out.println("👋 Exiting parking lot operations...");
                    return;
                default: 
                    System.out.println("❌ Invalid choice! Please enter 1-11");
                    continue;
            }
            
            System.out.println("\\n" + "-".repeat(30));
            System.out.println("Press Enter to continue...");
            sc.nextLine();
        }
    }
    
    private void parkVehicle(ParkingLot parkingLot) {
        System.out.println("\\n🚗 VEHICLE PARKING");
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
            
            if(VehicleFactory.isValidVehicleType(vehicleType)) {
                break;
            } else {
                System.out.println("❌ Invalid vehicle type! Please enter 2w, 3w, or 4w");
            }
        }
        
        try {
            Vehicle vehicle = VehicleFactory.createVehicle(vehicleType, vehicleColor, vehicleNumber, isEv);
            boolean parked = parkingService.parkVehicle(vehicle, parkingLot);
            
            if(parked) {
                System.out.println("✅ Vehicle parked successfully!");
                // Find the spot to show details
                ParkingSpot spot = parkingService.findAvailableSpot(parkingLot, vehicle);
                if(spot != null) {
                    System.out.println("📍 Spot ID: " + spot.parkingSpotId);
                }
            } else {
                System.out.println("❌ Sorry! No available parking spots for " + vehicleType.toUpperCase());
            }
        } catch (Exception e) {
            System.out.println("❌ Error parking vehicle: " + e.getMessage());
        }
    }
    
    private void exitVehicle(ParkingLot parkingLot) {
        System.out.println("\\n🚗 VEHICLE EXIT");
        System.out.println("-".repeat(15));
        System.out.print("Enter vehicle number: ");
        
        Scanner sc = InputUtil.getScanner();
        String vehicleNumber = sc.nextLine();
        
        boolean exited = parkingService.exitVehicle(vehicleNumber, parkingLot);
        
        if(exited) {
            System.out.println("✅ Vehicle exited successfully!");
        } else {
            System.out.println("❌ Ticket was not found for this vehicle");
        }
    }
    
    private void showAvailableSpots(ParkingLot parkingLot, String evSupport, ParkingSpotType parkingSpotType) {
        System.out.println("\\n📋 AVAILABLE SPOTS");
        System.out.println("-".repeat(20));
        
        boolean foundSpots = false;
        
        for(ParkingSpot parkingSpot : parkingLot.getParkingSpots()) {
            if(parkingSpot.isAvailable) {
                boolean shouldShow = false;
                
                if(parkingSpotType != null) {
                    if(parkingSpot.getParkingSpotType().equals(parkingSpotType)) {
                        shouldShow = true;
                    }
                } else if(evSupport != null) {
                    boolean spotHasCharger = parkingSpot.hasCharger;
                    if(evSupport.equals("true") && spotHasCharger) {
                        shouldShow = true;
                    } else if(evSupport.equals("false") && !spotHasCharger) {
                        shouldShow = true;
                    }
                } else {
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
    
    private void addSpot(ParkingLot parkingLot) {
        // Implementation for adding spots
        System.out.println("Add spot functionality - To be implemented");
    }
    
    private void updateSpot(ParkingLot parkingLot) {
        // Implementation for updating spots  
        System.out.println("Update spot functionality - To be implemented");
    }
    
    private void printParkOperations() {
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
}