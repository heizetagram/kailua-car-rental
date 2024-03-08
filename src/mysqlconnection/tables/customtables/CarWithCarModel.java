package mysqlconnection.tables.customtables;

import mysqlconnection.tables.CarType;
import mysqlconnection.tables.FuelType;

import java.time.LocalDate;

public class CarWithCarModel {
    private int carId;
    private int modelId;
    private FuelType fuelType;
    private CarType carType;
    private String registrationNumber;
    private LocalDate firstRegistrationDate;
    private int mileage;
    private String brand;
    private String model;

    public CarWithCarModel(int carId, int modelId, FuelType fuelType, CarType carType, String registrationNumber, LocalDate firstRegistrationDate, int mileage, String brand, String model) {
        this.carId = carId;
        this.modelId = modelId;
        this.fuelType = fuelType;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.firstRegistrationDate = firstRegistrationDate;
        this.mileage = mileage;
        this.brand = brand;
        this.model = model;
    }

    public int getCarId() {
        return carId;
    }

    public int getModelId() {
        return modelId;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public CarType getCarType() {
        return carType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public LocalDate getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public int getMileage() {
        return mileage;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}
