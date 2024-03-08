package infoprinter;

import mysqlconnection.tables.Customer;
import mysqlconnection.tables.customtables.CarWithCarModel;
import mysqlconnection.tables.customtables.CustomerWithCity;
import mysqlconnection.tables.customtables.RentalWithFullInfo;
import ui.ConsoleColors;
import ui.SystemMessages;

import java.time.format.DateTimeFormatter;

/**
 * @author David
 */

public class InfoPrinter {

    // Print customer (no more time)
    public void printCustomer(Customer customer) {
        System.out.printf("%sID:%s %s - %s %s%n", ConsoleColors.GREEN_BRIGHT, ConsoleColors.RESET,  customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
        System.out.printf("     %sAddress:%s %s, %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getAddress(), customer.getPostalCode());
        System.out.printf("     %sMobile:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getMobilePhone());
        System.out.printf("     %sPhone:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getPhoneNumber());
        System.out.printf("     %sE-mail:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getEmail());
        System.out.printf("     %sLicense Number:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getLicenseNumber());
        System.out.printf("     %sIssue Date:%s %s%n%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getIssueDate());
    }

    // Print customer with city
    public void printCustomerWithCity(CustomerWithCity customerWithCity) {
        SystemMessages.printGreenBrightText("\nCustomer ID: ");
        System.out.print(customerWithCity.getCustomerId() + " - ");
        System.out.print(customerWithCity.getFirstName() + " " + customerWithCity.getLastName() + "\n     ");
        SystemMessages.printGreenText("Address: ");
        System.out.print(customerWithCity.getAddress() + ", " + customerWithCity.getPostalCode() + ", " + customerWithCity.getCity() + "\n     ");
        SystemMessages.printGreenText("Mobile: ");
        System.out.print(customerWithCity.getMobilePhone() + "\n     ");
        SystemMessages.printGreenText("Phone:  ");
        System.out.print(customerWithCity.getPhoneNumber() + "\n     ");
        SystemMessages.printGreenText("E-mail: ");
        System.out.print(customerWithCity.getEmail() + "\n     ");
        SystemMessages.printGreenText("License Number: ");
        System.out.print(customerWithCity.getLicenseNumber() + "\n     ");
        SystemMessages.printGreenText("License Issue Date: ");
        System.out.print(customerWithCity.getIssueDate() + "\n");
    }

    // Print car with full info
    public void printFullCarInfo(CarWithCarModel fullCar) {
        SystemMessages.printGreenBrightText("\nCar ID: ");
        System.out.print(fullCar.getCarId() + " - ");
        System.out.print(fullCar.getBrand() + ", " + fullCar.getModel() + "\n     ");
        SystemMessages.printGreenText("Fuel type: ");
        System.out.print(fullCar.getFuelType() + "\n     ");
        SystemMessages.printGreenText("Car type: ");
        System.out.print(fullCar.getCarType() + "\n     ");
        SystemMessages.printGreenText("Registration number: ");
        System.out.print(fullCar.getRegistrationNumber() + "\n     ");
        SystemMessages.printGreenText("First registration date: ");
        System.out.print(fullCar.getFirstRegistrationDate() + "\n     ");
        SystemMessages.printGreenText("Mileage: ");
        System.out.print(fullCar.getMileage() + " km\n");
    }

    // Print rental with full info
    public void printRentalFullInfo(RentalWithFullInfo rentalFullInfo) { // CONTINUE HERE
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm");
        SystemMessages.printGreenBrightText("\nRental ID: ");
        System.out.print(rentalFullInfo.getRentalId() + "\n     ");
        SystemMessages.printGreenText("Customer ID: ");
        System.out.print(rentalFullInfo.getCustomerId() + " - " + rentalFullInfo.getFirstName() + ", " + rentalFullInfo.getLastName() + "\n     ");
        SystemMessages.printGreenText("Car ID: ");
        System.out.print(rentalFullInfo.getCarId() + " - " + rentalFullInfo.getBrand() +  ", " + rentalFullInfo.getModel() + "\n     ");
        SystemMessages.printGreenText("Registration number: ");
        System.out.print(rentalFullInfo.getRegistrationNumber() + "\n     ");
        SystemMessages.printGreenText("Rented from: ");
        System.out.print(rentalFullInfo.getFromDate().format(formatter) + "\n     ");
        SystemMessages.printGreenText("Rented until: ");
        System.out.print(rentalFullInfo.getToDate().format(formatter) + "\n     ");
        SystemMessages.printGreenText("Max km: ");
        System.out.print(rentalFullInfo.getMaxKm() + "\n     ");
        SystemMessages.printGreenText("Current km: ");
        System.out.print(rentalFullInfo.getCurrentKm() + "\n     ");
    }
}
