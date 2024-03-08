package mysqlconnection.tables.customtables;

import mysqlconnection.tables.Rental;

import java.time.LocalDateTime;

public class RentalWithFullInfo extends Rental {
    private String firstName;
    private String lastName;
    private String registrationNumber;
    private String brand;
    private String model;

    public RentalWithFullInfo(int rentalId, int customerId, int carId, LocalDateTime fromDate, LocalDateTime toDate, int maxKm, int currentKm, String firstName, String lastName, String registrationNumber, String brand, String model) {
        super(rentalId, customerId, carId, fromDate, toDate, maxKm, currentKm);
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
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
