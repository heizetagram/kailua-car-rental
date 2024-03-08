package mysqlconnection.tables.customtables;

import mysqlconnection.tables.Car;
import mysqlconnection.tables.CarType;
import mysqlconnection.tables.FuelType;

import java.time.LocalDate;

public class CarWithCarModel extends Car {
    private String brand;
    private String model;

    public CarWithCarModel(int carId, int modelId, FuelType fuelType, CarType carType, String registrationNumber, LocalDate firstRegistrationDate, int mileage, String brand, String model) {
        super(carId, modelId, fuelType, carType, registrationNumber, firstRegistrationDate, mileage);
        this.brand = brand;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}
