
import menu.Menu;
import mysqlconnection.datamodification.DisplayModification;
import system.SystemRunning;
import mysqlconnection.datamodification.CarModification;
import mysqlconnection.datamodification.CustomerModification;
import mysqlconnection.MySqlConnection;
import mysqlconnection.datamodification.RentalModification;
import ui.SystemMessages;
import ui.UI;

import java.util.Scanner;

/**
 * @author David
 * @author Frederik
 */

public class Main {
    private MySqlConnection mySqlConnection;
    private CustomerModification customerModification;
    private CarModification carModification;
    private RentalModification rentalModification;
    private DisplayModification displayModification;

    public Main() {
        mySqlConnection = new MySqlConnection();
        carModification = new CarModification(mySqlConnection);
        customerModification = new CustomerModification(mySqlConnection);
        rentalModification = new RentalModification(mySqlConnection);
        displayModification = new DisplayModification(mySqlConnection);
        SystemRunning.setRunning(true);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    // Run method
    private void run() {
        Menu menu = new Menu();
        int choice;
        do {
            menu.displayMainMenu();
            menu.printEnterChoice(1, 4);
            choice = UI.promptInt();
            UI.promptString(); // Scanner bug

            switch (choice) {
                case 1:
                    menu.carSubMenu(carModification);
                    break;
                case 2:
                    menu.customerSubMenu(customerModification);
                    break;
                case 3:
                    menu.rentalSubMenu(rentalModification);
                    break;
                case 4:
                    menu.displayDisplaySubMenu(displayModification);
                    break;
                case 5:
                    SystemMessages.printQuitProgram();
                    SystemRunning.setRunning(false);
                    break;
                default:
                    SystemMessages.printError("Invalid choice. Please enter a number between 1-4");
            }
        } while (SystemRunning.isRunning());
        mySqlConnection.closeConnection();
    }
}

