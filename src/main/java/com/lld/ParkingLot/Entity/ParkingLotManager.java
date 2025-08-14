package com.lld.ParkingLot.Entity;

import com.lld.InputUtil;
import com.lld.ParkingLot.Entity.Parking.ParkingLot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpot;
import com.lld.ParkingLot.Entity.Parking.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
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


    public void addNewParkingLot(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
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
