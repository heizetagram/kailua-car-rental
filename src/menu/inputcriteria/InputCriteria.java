package menu.inputcriteria;

import ui.SystemMessages;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputCriteria {
    // Check if user input is an integer (doesn't work properly, have to press enter 2x after inputting a non-integer)
    public int checkIfUserInputIsInt(int min, int max) {
        boolean running = true;
        Scanner scan = new Scanner(System.in);
        int userChoice = 0;

        while (running) {
            try {
                userChoice = scan.nextInt();
                userChoice = checkIfIntIsBetweenMinAndMax(userChoice, min, max);
                running = false;
            } catch (InputMismatchException e) {
                SystemMessages.printError("Input must be an integer\n");
                SystemMessages.printTryAgain();
                scan.nextLine(); // Scanner bug
            }
        }
        return userChoice;
    }

    // Check if integer is between a user-given min and max
    private int checkIfIntIsBetweenMinAndMax(int userChoice, int min, int max) {
        Scanner scan = new Scanner(System.in);
        while (userChoice < min || userChoice > max) {
            try {
                SystemMessages.printError("Input must be between " + min + "-" + max + "\n");
                SystemMessages.printTryAgain();
                userChoice = scan.nextInt();
            } catch (InputMismatchException e) {
                userChoice = checkIfUserInputIsInt(min, max);
            }
        }
        return userChoice;
    }
}
