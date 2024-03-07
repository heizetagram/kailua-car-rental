package mysqlconnection.tables;

import java.time.LocalDate;

/**
 * @author Freddy
 */

public class Car {
    private int carId;
    private int modelId;
    private CarType carType;
    private FuelType fuelType;
    private String registrationNumber;
    private LocalDate firstRegistrationDate;
    private int mileage;

    public Car(int carId, int modelId, FuelType fuelType, CarType carType, String registrationNumber,
               LocalDate firstRegistrationDate, int mileage) {
        this.carId = carId;
        this.modelId = modelId;
        this.carType = carType;
        this.fuelType = fuelType;
        this.registrationNumber = registrationNumber;
        this.firstRegistrationDate = firstRegistrationDate;
        this.mileage = mileage;
    }

    public Car(int modelId, FuelType fuelType, CarType carType, String registrationNumber,
               LocalDate firstRegistrationDate, int mileage) {
        this.modelId = modelId;
        this.fuelType = fuelType;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.firstRegistrationDate = firstRegistrationDate;
        this.mileage = mileage;
    }


    public int getCarId() {
        return carId;
    }

    public int getModelId() {
        return modelId;
    }

    public CarType getCarType() {
        return carType;
    }

    public FuelType getFuelType() {
        return fuelType;
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

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setModelId(int ModelId) {
        this.modelId = modelId;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setFirstRegistrationDate(LocalDate firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carModelId=" + modelId +
                ", carType=" + carType +
                ", fuelType=" + fuelType +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", firstRegistrationDate=" + firstRegistrationDate +
                ", mileage=" + mileage +
                '}';
    }

}
