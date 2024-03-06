package tables;

/**
 * @author Freddy
 */

public enum CarType {
    FAMILY,
    SPORT,
    LUXURY;

    public static CarType valueOfIgnoreCase(String value) {
        for (CarType carType : values()) {
            if (carType.name().equalsIgnoreCase(value.trim())) {
                return carType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + CarType.class + "." + value);
    }
}
