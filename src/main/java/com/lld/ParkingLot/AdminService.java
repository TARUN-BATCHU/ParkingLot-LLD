package com.lld.ParkingLot;

import com.lld.InputUtil;
import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    public void createNewParkingLot(){
            printNewParkingLotOperations();
    }

    public void login(){
        ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
        List<ParkingLot> parkingLots = parkingLotManager.showAllParkingLots();
        
        if(parkingLots.isEmpty()) {
            System.out.println("❌ No parking lots available! Please create one first.");
            return;
        }
        
        System.out.println("\n🏢 AVAILABLE PARKING LOTS");
        System.out.println("=".repeat(40));
        
        for(int i=0; i<parkingLots.size(); i++){
            System.out.println((i+1) + ". ID: " + parkingLots.get(i).parkingLotId + 
                              " | Name: " + parkingLots.get(i).parkingLotName +
                              " | Spots: " + parkingLots.get(i).parkingSpots.size());
        }
        
        Scanner sc = InputUtil.getScanner();
        
        while(true) {
            System.out.print("\nSelect parking lot (1-" + parkingLots.size() + "): ");
            
            if(!sc.hasNextInt()) {
                System.out.println("❌ Please enter a valid number!");
                sc.nextLine();
                continue;
            }
            
            int parkingLotId = sc.nextInt() - 1;
            sc.nextLine();
            
            if(parkingLotId < 0 || parkingLotId >= parkingLots.size()){
                System.out.println("❌ Invalid selection! Please choose 1-" + parkingLots.size());
                continue;
            }
            
            parkingLotManager.parkOperations(parkingLotId);
            break;
        }
    }

    public void printNewParkingLotOperations(){
        System.out.println("WELCOME TO OUR NEW PARKINGLOT ");
        Scanner sc = InputUtil.getScanner();
        System.out.println("Enter name of your parkingLot");
        String name = sc.nextLine();
        System.out.println("Enter number of available parking spots");
        int spots = sc.nextInt();
        sc.nextLine();
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for(int i=0; i<spots; i++){
            System.out.println("Spot " + (i+1) + ":");
            
            // Loop until valid spot type is entered
            String spotType = null;
            ParkingSpotType parkingSpotType = null;
            while(true) {
                System.out.println("Enter spot type (2w,3w,4w)");
                spotType = sc.nextLine();
                parkingSpotType = findParkingSpotType(spotType);
                
                if(parkingSpotType != null) {
                    break; // Valid input, exit loop
                } else {
                    System.out.println("❌ Invalid spot type! Please enter 2w, 3w, or 4w");
                }
            }
            
            System.out.println("is EV supported");
            boolean hasCharger = sc.nextBoolean();
            sc.nextLine();
            System.out.println("floor number");
            int floorNumber = sc.nextInt();
            sc.nextLine();
            int spotId = i+1;
            ParkingSpot parkingSpot = new ParkingSpot(spotId,parkingSpotType,hasCharger,true,floorNumber,null);
            parkingSpots.add(parkingSpot);
        }
        ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
        int parkinLotId = 1;
        if(parkingLotManager.parkingLots!=null){
            parkinLotId = parkingLotManager.parkingLots.size()+1;
        }
        ParkingLot parkingLot = new ParkingLot(parkinLotId,name,parkingSpots,null);
        parkingLotManager.addNewParkingLot(parkingLot);
        System.out.println("Done creating a new Parking Lot");
    }

    public ParkingSpotType findParkingSpotType(String spotType){
        if(spotType.equalsIgnoreCase("2w")){
            return ParkingSpotType.TWO_WHEELER;
        }else if(spotType.equalsIgnoreCase("3w")){
            return ParkingSpotType.THREE_WHEELER;
        }else if(spotType.equalsIgnoreCase("4w")){
            return ParkingSpotType.FOUR_WHEELER;
        }else{
            return null; // Return null instead of throwing exception
        }
    }
}
