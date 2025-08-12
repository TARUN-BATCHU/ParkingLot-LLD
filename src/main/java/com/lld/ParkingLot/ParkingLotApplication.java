package com.lld.ParkingLot;


import com.lld.InputUtil;

import java.util.Scanner;

public class ParkingLotApplication{
public static void main (String args[]){

	AdminService adminService = new AdminService();

	while (true) {
		Scanner adminScanner = InputUtil.getScanner();
		printAdminOptions();
		int selectedOperation = adminScanner.nextInt();
		adminScanner.nextLine();
		switch (selectedOperation) {
			case 1:
				adminService.createNewParkingLot(); break;
			case 2:
				adminService.login(); break;
			case 3:
				System.exit(0); break;
			default:
				System.out.println("Invalid Choice"); break;
		}
	}
}
	public static void printAdminOptions(){
		System.out.println("WELCOME TO OUR PARKING ADMIN");
		System.out.println("1 . Create new parking Lot");
		System.out.println("2 . Login to my Parking Lot");
		System.out.println("3 . Exit");
	}
}
