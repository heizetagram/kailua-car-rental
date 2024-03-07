
import mysqlconnection.datamodification.CarModification;
import mysqlconnection.datamodification.CustomerModification;
import mysqlconnection.MySqlConnection;

import java.util.Scanner;

/**
 * @author David
 * @author Frederik
 */

public class Main {
    private MySqlConnection mySqlConnection;
    private CustomerModification customerModification;
    private CarModification carModification;

    public Main() {
        mySqlConnection = new MySqlConnection();
        carModification = new CarModification(mySqlConnection);
        customerModification = new CustomerModification(mySqlConnection);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    // Run method
    private void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMainMenu();
            System.out.print("Enter your choice (1-4): ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    carSubMenu();
                    break;
                case 2:
                    customerSubMenu();
                    break;
                case 3:
                    // mangler logic
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }

        } while (choice != 4);

        mySqlConnection.closeConnection();
    }

    private void displayMainMenu() {
        System.out.println("\nCAR RENTAL SYSTEM MENU");
        System.out.println("1. Cars");
        System.out.println("2. Customers");
        System.out.println("3. Display");
        System.out.println("4. Quit");
    }

    private void displayCarMenu() {
        System.out.println("\nCAR MENU");
        System.out.println("1. Add a car");
        System.out.println("2. Update a car");
        System.out.println("3. Delete a car");
    }

    private void carSubMenu() {
        Scanner scanner = new Scanner(System.in);
        int carChoice;

        do {
            displayCarMenu();
            System.out.print("Enter your choice (1-2): ");
            carChoice = scanner.nextInt();
            scanner.nextLine();

            switch (carChoice) {
                case 1:
                    carModification.createCar();
                    break;
                case 2:
                    carModification.updateCar();
                case 3:
                    deleteCar();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }

        } while (carChoice != 2);
    }

    private void displayCustomerMenu() {
        System.out.println("\nCUSTOMER MENU");
        // kunde menu
    }

    private void customerSubMenu() {
        Scanner scanner = new Scanner(System.in);
        int customerChoice;

        do {
            displayCustomerMenu();
            System.out.print("Enter your choice (1-2): ");
            customerChoice = scanner.nextInt();
            scanner.nextLine();

            // kunde relations

        } while (customerChoice != 2);
    }


    private void deleteCar() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter car ID to delete: ");
        int carId = in.nextInt();

        carModification.deleteCarById(carId);
    }

}

