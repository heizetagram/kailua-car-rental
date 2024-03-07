package menu;

import menu.inputcriteria.InputCriteria;
import mysqlconnection.tables.CarType;
import mysqlconnection.tables.FuelType;
import ui.SystemMessages;

/**
 * @author David
 */

public class MenuOption {
    private InputCriteria inputCriteria;

    public MenuOption() {
        inputCriteria = new InputCriteria();
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
}
