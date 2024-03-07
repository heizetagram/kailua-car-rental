package infoprinter;

import mysqlconnection.tables.Customer;
import ui.ConsoleColors;

/**
 * @author David
 */

public class InfoPrinter {

    public void printCustomer(Customer customer) {
        System.out.printf("%sID:%s %s, %s %s%n", ConsoleColors.GREEN_BRIGHT, ConsoleColors.RESET,  customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
        System.out.printf("     %sAddress:%s %s, %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getAddress(), customer.getPostalCode());
        System.out.printf("     %sMobile:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getMobilePhone());
        System.out.printf("     %sPhone:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getPhoneNumber());
        System.out.printf("     %sE-mail:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getEmail());
        System.out.printf("     %sLicense Number:%s %s%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getLicense_number());
        System.out.printf("     %sIssue Date:%s %s%n%n", ConsoleColors.GREEN, ConsoleColors.RESET, customer.getIssueDate());
    }

/*
    @Override
    public String toString() {
        return "ID: " + customerId +
                ": " + firstName +
                ", "+ lastName +
                " - " + address +
                ", " + postalCode +
                ", Mobile: " + mobilePhone +
                ", Phone: " + phoneNumber +
                ", " + email +
                ", License number: " + license_number +
                ", Issue date: " + issueDate;
    }

 */
}
