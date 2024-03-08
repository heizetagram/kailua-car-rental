package menu;

import menu.inputcriteria.InputCriteria;
import mysqlconnection.datamodification.CarModification;
import mysqlconnection.datamodification.CustomerModification;
import mysqlconnection.datamodification.DisplayModification;
import mysqlconnection.datamodification.RentalModification;
import mysqlconnection.tables.CarType;
import mysqlconnection.tables.FuelType;
import system.SystemRunning;
import ui.SystemMessages;
import ui.UI;

import java.util.Scanner;

/**
 * @author Freddy
 * @author David
 */

public class Menu {
    private InputCriteria inputCriteria;

    public Menu() {
        inputCriteria = new InputCriteria();
    }

    // Car sub menu
    public void carSubMenu(CarModification carModification) {
        Scanner scanner = new Scanner(System.in);
        int carChoice;

        do {
            displayCarMenu();
            printEnterChoice(1, 4);
            carChoice = scanner.nextInt();
            scanner.nextLine();

            switch (carChoice) {
                case 1:
                    carModification.createCar();
                    break;
                case 2:
                    carModification.updateCar();
                    break;
                case 3:
                    carModification.deleteCar();
                    break;
                case 4:
                    SystemMessages.printQuitProgram();
                    SystemRunning.setRunning(false);
                    break;
                default:
                    SystemMessages.printError("Invalid choice. Please enter a number between 1-4\n");
            }
        } while (SystemRunning.isRunning());
    }

    // Customer sub menu
    public void customerSubMenu(CustomerModification customerModification) {
        Scanner scanner = new Scanner(System.in);
        int customerChoice;

        do {
            displayCustomerMenu();
            printEnterChoice(1, 4);
            customerChoice = scanner.nextInt();
            scanner.nextLine();

            switch (customerChoice) {
                case 1 -> customerModification.createCustomer();
                case 2 -> customerModification.updateCustomer();
                case 3 -> customerModification.deleteCustomer();
                case 4 -> {SystemMessages.printQuitProgram(); SystemRunning.setRunning(false);}
                default -> SystemMessages.printError("Invalid choice. Please enter a number between 1-4");
            }
        } while (SystemRunning.isRunning());
    }

    // Rental sub menu
    public void rentalSubMenu(RentalModification rentalModification) {
        int rentalChoice;
        do {
            displayRentalMenu();
            printEnterChoice(1, 4);
            rentalChoice = UI.promptInt();
            UI.promptString(); // Scanner bug

            switch (rentalChoice) {
                case 1 -> rentalModification.createRental();
                case 2 -> rentalModification.updateRental();
                case 3 -> rentalModification.deleteRental();
                case 4 -> {SystemMessages.printQuitProgram(); SystemRunning.setRunning(false);}
                default -> SystemMessages.printError("Invalid choice. Please enter a number between 1-4");
            }
        } while (SystemRunning.isRunning());
    }

    // Display sub menu
    public void displayDisplaySubMenu(DisplayModification displayModification) {
        int displayChoice;
        do {
            displayDisplayMenu();
            printEnterChoice(1, 4);
            displayChoice = UI.promptInt();
            UI.promptString(); // Scanner bug

            switch (displayChoice) {
                case 1 -> displayModification.printAllCars();
                case 2 -> displayModification.printAllCustomers();
                case 3 -> displayModification.printAllRentals();
                case 4 -> {SystemMessages.printQuitProgram(); SystemRunning.setRunning(false);}
                default -> SystemMessages.printError("Invalid choice. Please enter a number between 1-4");
            }
        } while (SystemRunning.isRunning());
    }

    // Display main menu
    public void displayMainMenu() {
        SystemMessages.printBoldBlueText("CAR RENTAL SYSTEM MENU\n");
        SystemMessages.printBrightPurpleText("1. Cars\n");
        SystemMessages.printBrightCyanText("2. Customers\n");
        SystemMessages.printCyanText("3. Rentals\n");
        SystemMessages.printBlueText("4. Display\n");
        SystemMessages.printQuitOption(5);
    }

    // Display rental menu
    private void displayRentalMenu() {
        SystemMessages.printBoldCyanText("\nRENTAL MENU\n");
        SystemMessages.printCyanText("1. Create a rental\n");
        SystemMessages.printCyanText("2. Update a rental\n");
        SystemMessages.printCyanText("3. Delete a rental\n");
        SystemMessages.printQuitOption(4);
    }

    // Display car menu
    private void displayCarMenu() {
        SystemMessages.printBoldBrightPurpleText("\nCAR MENU\n");
        SystemMessages.printBrightPurpleText("1. Add a car\n");
        SystemMessages.printBrightPurpleText("2. Update a car\n");
        SystemMessages.printBrightPurpleText("3. Delete a car\n");
        SystemMessages.printQuitOption(4);
    }

    // Display customer menu
    private void displayCustomerMenu() {
        SystemMessages.printBoldBrightCyanText("\nCUSTOMER MENU\n");
        SystemMessages.printBrightCyanText("1. Add a customer\n");
        SystemMessages.printBrightCyanText("2. Update a customer\n");
        SystemMessages.printBrightCyanText("3. Delete a customer\n");
        SystemMessages.printQuitOption(4);
    }

    // Display display menu
    private void displayDisplayMenu() {
        SystemMessages.printBoldBlueText("\nDISPLAY MENU\n");
        SystemMessages.printBlueText("1. Show All Cars\n");
        SystemMessages.printBlueText("2. Show All Customers\n");
        SystemMessages.printBlueText("3. Show All Rentals\n");
        SystemMessages.printQuitOption(4);
    }

    // Show fuel type options
    public void showFuelTypeOptions() {
        System.out.println();
        SystemMessages.printYellowText("   (1) Gasoline\n");
        SystemMessages.printYellowText("   (2) Diesel\n");
        SystemMessages.printYellowText("   (3) Electric\n");
        SystemMessages.printYellowText("   (4) Hybrid\n");
    }

    // Choose fuel type
    public FuelType chooseFuelType() {
        int userChoice = inputCriteria.checkIfUserInputIsInt(1, 4);
        switch (userChoice) {
            case 1 -> {return FuelType.GASOLINE;}
            case 2 -> {return FuelType.DIESEL;}
            case 3 -> {return FuelType.ELECTRIC;}
            case 4 -> {return FuelType.HYBRID;}
            default -> {return null;}
        }
    }

    // Show car type options
    public void showCarTypeOptions() {
        System.out.println();
        SystemMessages.printYellowText("   (1) Family\n");
        SystemMessages.printYellowText("   (2) Sport\n");
        SystemMessages.printYellowText("   (3) Luxury\n");
    }

    // Choose car type
    public CarType chooseCarType() {
        int userChoice = inputCriteria.checkIfUserInputIsInt(1, 3);
        switch (userChoice) {
            case 1 -> {return CarType.FAMILY;}
            case 2 -> {return CarType.SPORT;}
            case 3 -> {return CarType.LUXURY;}
            default -> {return null;}
        }
    }

    // Print enter choice
    public void printEnterChoice(int min, int max) {
        SystemMessages.printYellowText("Enter your choice (" + min + "-" + max + "): ");
    }
}
