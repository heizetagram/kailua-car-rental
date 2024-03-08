package ui;

/**
 * @author David
 */

public class SystemMessages {
    public static void printSuccess(String text) {
        System.out.printf("%s%s%s", ConsoleColors.GREEN_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printError(String text) {
        System.out.printf("%s%s%s", ConsoleColors.RED, text, ConsoleColors.RESET);
    }

    public static void printTryAgain() {
        printError("Please try again...\n");
    }

    public static void printQuitProgram() {
        SystemMessages.printBrightRedText("Exiting the program. Goodbye!");
    }

    public static void printQuitOption(int n) {
        printBrightRedText(n + ". Quit\n");
    }

    public static void printGreenBrightText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.GREEN_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printGreenText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.GREEN, text, ConsoleColors.RESET);
    }

    public static void printBoldYellowText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.YELLOW_BOLD, text, ConsoleColors.RESET);
    }

    public static void printYellowText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.YELLOW, text, ConsoleColors.RESET);
    }

    public static void printBoldBlueText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.BLUE_BOLD, text, ConsoleColors.RESET);
    }

    public static void printBlueText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.BLUE, text, ConsoleColors.RESET);
    }

    public static void printBoldBrightCyanText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.CYAN_BOLD_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printBrightCyanText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.CYAN_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printBoldBrightPurpleText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.PURPLE_BOLD_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printBrightPurpleText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.PURPLE_BRIGHT, text, ConsoleColors.RESET);
    }

    public static void printCyanText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.CYAN, text, ConsoleColors.RESET);
    }

    public static void printBoldCyanText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.CYAN_BOLD, text, ConsoleColors.RESET);
    }

    public static void printBrightRedText(String text) {
        System.out.printf("%s%s%s", ConsoleColors.RED_BRIGHT, text, ConsoleColors.RESET);
    }
}
