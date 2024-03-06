package datamodification;

import menu.MenuOption;
import mysqlconnection.MySqlConnection;
import tables.Car;
import tables.CarModel;
import tables.CarType;
import tables.FuelType;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Freddy
 * @author David
 */

public class CarModification {
    private MySqlConnection mySqlConnection;
    private Connection connection;

    public CarModification(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
        connection = mySqlConnection.getConnection();
    }

    public void insertCarIntoDatabase(Car car) {
        try {
            String query = "INSERT INTO car (model_id, fuel_type_name, car_type_name, registration_number, first_registration_date, mileage) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, car.getCarModel().getModelId());
            pstmt.setString(2, car.getFuelType().name());
            pstmt.setString(3, car.getCarType().name());
            pstmt.setString(4, car.getRegistrationNumber());
            pstmt.setDate(5, Date.valueOf(car.getFirstRegistrationDate()));
            pstmt.setInt(6, car.getMileage());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(int carId) {
        String query = "DELETE FROM car WHERE car_id = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, carId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CarType getCarTypeByName(String typeName) {
        String query = "SELECT * FROM car_type WHERE car_type_name = ?";
        CarType carType = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, typeName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                carType = CarType.valueOfIgnoreCase(rs.getString("car_type_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carType;
    }

    // Create car
    public void createCar() {
        Car car = userTypesCar();
        insertCarIntoDatabase(car);
    }

    // Prompt user for car information
    private Car userTypesCar() {
        CarModelModification carModelModification = new CarModelModification(mySqlConnection);
        MenuOption menuOption = new MenuOption();
        SystemMessages.printYellowBoldText("CREATE CAR\n");
        CarModel carModel = carModelModification.createCarModel();


        SystemMessages.printYellowText("Fuel Type: ");
        menuOption.showFuelTypeOptions();
        FuelType fuelType = menuOption.chooseFuelType();

        SystemMessages.printYellowText("Car Type: ");
        menuOption.showCarTypeOptions();
        CarType carType = menuOption.chooseCarType();

        SystemMessages.printYellowText("Registration Number: ");
        String registrationNumber = UI.promptString();

        SystemMessages.printYellowText("First Registration Date (DD-MM-YYYY): ");
        String dateString = UI.promptString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate firstRegistrationDate = LocalDate.parse(dateString, formatter);

        SystemMessages.printYellowText("Mileage: ");
        int mileage = UI.promptInt();
        UI.promptString(); // Scanner bug

        return new Car(carModel, fuelType, carType, registrationNumber, firstRegistrationDate, mileage);
    }
}
