package tables;

import java.time.LocalDate;

/**
 * @author Freddy
 */

public class Car {
    private int carId;
    private CarModel carModel;
    private CarType carType;
    private FuelType fuelType;
    private String registrationNumber;
    private LocalDate firstRegistrationDate;
    private int mileage;

    public Car(int carId, CarModel carModel, FuelType fuelType, CarType carType, String registrationNumber,
               LocalDate firstRegistrationDate, int mileage) {
        this.carId = carId;
        this.carModel = carModel;
        this.carType = carType;
        this.fuelType = fuelType;
        this.registrationNumber = registrationNumber;
        this.firstRegistrationDate = firstRegistrationDate;
        this.mileage = mileage;
    }

    public Car(CarModel carModel, FuelType fuelType, CarType carType, String registrationNumber,
               LocalDate firstRegistrationDate, int mileage) {
        this.carModel = carModel;
        this.fuelType = fuelType;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.firstRegistrationDate = firstRegistrationDate;
        this.mileage = mileage;
    }


    public int getCarId() {
        return carId;
    }

    public CarModel getCarModel() {
        return carModel;
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

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
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
                ", carModel=" + carModel +
                ", carType=" + carType +
                ", fuelType=" + fuelType +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", firstRegistrationDate=" + firstRegistrationDate +
                ", mileage=" + mileage +
                '}';
    }

}
