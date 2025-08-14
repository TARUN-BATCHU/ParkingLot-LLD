package com.lld.ParkingLot.Controller;

import com.lld.InputUtil;
import com.lld.ParkingLot.AdminService;
import com.lld.ParkingLot.AdminServiceImpl;
import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    
    private AdminService adminService;
    private ParkingController parkingController;
    
    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.parkingController = new ParkingController();
    }
    
    public void createNewParkingLot() {
        System.out.println("WELCOME TO OUR NEW PARKINGLOT");
        Scanner sc = InputUtil.getScanner();
        
        System.out.println("Enter name of your parkingLot");
        String name = sc.nextLine();
        
        System.out.println("Enter number of available parking spots");
        int spots = sc.nextInt();
        sc.nextLine();
        
        List<AdminService.ParkingSpotData> spotDataList = new ArrayList<>();
        
        for(int i = 0; i < spots; i++) {
            System.out.println("Spot " + (i+1) + ":");
            
            String spotType = null;
            ParkingSpotType parkingSpotType = null;
            while(true) {
                System.out.println("Enter spot type (2w,3w,4w)");
                spotType = sc.nextLine();
                parkingSpotType = adminService.findParkingSpotType(spotType);
                
                if(parkingSpotType != null) {
                    break;
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
            
            spotDataList.add(new AdminService.ParkingSpotData(spotType, hasCharger, floorNumber));
        }
        
        try {
            ParkingLot parkingLot = adminService.createNewParkingLot(name, spotDataList);
            System.out.println("✅ Done creating a new Parking Lot: " + parkingLot.parkingLotName);
        } catch (Exception e) {
            System.out.println("❌ Error creating parking lot: " + e.getMessage());
        }
    }
    
    public void login() {
        List<ParkingLot> parkingLots = adminService.getAllParkingLots();
        
        if(parkingLots.isEmpty()) {
            System.out.println("❌ No parking lots available! Please create one first.");
            return;
        }
        
        System.out.println("\n🏢 AVAILABLE PARKING LOTS");
        System.out.println("=".repeat(40));
        
        for(int i = 0; i < parkingLots.size(); i++) {
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
            
            if(parkingLotId < 0 || parkingLotId >= parkingLots.size()) {
                System.out.println("❌ Invalid selection! Please choose 1-" + parkingLots.size());
                continue;
            }
            
            parkingController.parkOperations(parkingLots.get(parkingLotId));
            break;
        }
    }
    
    public static void printAdminOptions() {
        System.out.println("WELCOME TO OUR PARKING ADMIN");
        System.out.println("1. Create new parking Lot");
        System.out.println("2. Login to my Parking Lot");
        System.out.println("3. Exit");
    }
}