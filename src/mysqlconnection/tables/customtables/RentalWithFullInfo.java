package mysqlconnection.tables.customtables;

import java.time.LocalDateTime;

public class RentalWithFullInfo {
    private int rentalId;
    private int customerId;
    private int carId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int maxKm;
    private int currentKm;
    private String firstName;
    private String lastName;
    private String registrationNumber;
    private String brand;
    private String model;

    public RentalWithFullInfo(int rentalId, int customerId, int carId, LocalDateTime fromDate, LocalDateTime toDate, int maxKm, int currentKm, String firstName, String lastName, String registrationNumber, String brand, String model) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.maxKm = maxKm;
        this.currentKm = currentKm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCarId() {
        return carId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public int getMaxKm() {
        return maxKm;
    }

    public int getCurrentKm() {
        return currentKm;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}
