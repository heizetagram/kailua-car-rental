package ui;

import java.util.Scanner;

/**
 * @author David
 */

public class UI {
    private static final Scanner scan;

    public static String promptString() {
        return scan.nextLine();
    }

    public static int promptInt() {
        return scan.nextInt();
    }

    static {
        scan = new Scanner(System.in);
    }

    public static String promptFirstName() {
        SystemMessages.printYellowText("First name: ");
        return UI.promptString();
    }

    public static String promptLastName() {
        SystemMessages.printYellowText("Last name: ");
        return UI.promptString();
    }
}
