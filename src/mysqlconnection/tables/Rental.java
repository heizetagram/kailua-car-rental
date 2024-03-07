package mysqlconnection.tables;

import java.time.LocalDateTime;

public class Rental {
    private int rentalId;
    private int customerId;
    private int carId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int maxKm;
    private int currentKm;

    public Rental(int rentalId, int customerId, int carId, LocalDateTime fromDate, LocalDateTime toDate, int maxKm, int currentKm) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.maxKm = maxKm;
        this.currentKm = currentKm;
    }

    public Rental(int customerId, int carId, LocalDateTime fromDate, LocalDateTime toDate, int maxKm, int currentKm) {
        this.customerId = customerId;
        this.carId = carId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.maxKm = maxKm;
        this.currentKm = currentKm;
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

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public void setMaxKm(int maxKm) {
        this.maxKm = maxKm;
    }

    public void setCurrentKm(int currentKm) {
        this.currentKm = currentKm;
    }
}
