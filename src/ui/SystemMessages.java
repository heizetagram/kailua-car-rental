package ui;

/**
 * @author David
 */

public class SystemMessages {
    public static void printSuccess(String text) {
        System.out.printf("%s%s%s", ConsoleColors.GREEN_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printYellowBoldText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.YELLOW_BOLD, text, ConsoleColors.RESET);
    }

    public static void printYellowText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.YELLOW, text, ConsoleColors.RESET);
    }

    public static void printError(String text) {
        System.out.printf("%s%s%s", ConsoleColors.RED, text, ConsoleColors.RESET);
    }

    public static void tryAgain() {
        printError("Please try again...\n");
    }
}
