package ui;

import java.util.Scanner;

/**
 * @author David
 */

public class UI {
    private static final Scanner scan;

    public static void print(String text) {
        System.out.print(text);
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void printf(String text, Object... args) {
        System.out.printf(text, args);
    }

    public static String promptString() {
        return scan.nextLine();
    }

    public static int promptInt() {
        return scan.nextInt();
    }

    static {
        scan = new Scanner(System.in);
    }
}
