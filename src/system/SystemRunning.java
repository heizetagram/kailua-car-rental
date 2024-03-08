package system;

public class SystemRunning {
    private static boolean isRunning;

    // Getter
    public static boolean isRunning() {
        return isRunning;
    }

    // Setter
    public static void setRunning(boolean isRunning) {
        SystemRunning.isRunning = isRunning;
    }
}