package com.lld.ParkingLot;

import com.lld.InputUtil;
import com.lld.ParkingLot.Controller.AdminController;

import java.util.Scanner;

public class ParkingLotApplication {

	public static void main(String[] args) {
		AdminController adminController = new AdminController();

		while (true) {
			Scanner adminScanner = InputUtil.getScanner();
			AdminController.printAdminOptions();

			if (!adminScanner.hasNextInt()) {
				System.out.println("❌ Please enter a valid number!");
				adminScanner.nextLine();
				continue;
			}

			int selectedOperation = adminScanner.nextInt();
			adminScanner.nextLine();

			switch (selectedOperation) {
				case 1:
					adminController.createNewParkingLot();
					break;
				case 2:
					adminController.login();
					break;
				case 3:
					System.out.println("👋 Thank you for using our parking system!");
					InputUtil.closeScanner();
					System.exit(0);
					break;
				default:
					System.out.println("❌ Invalid Choice! Please enter 1-3");
					break;
			}
		}
	}
}

