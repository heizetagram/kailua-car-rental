package mysqlconnection.datamodification;

import menu.MenuOption;
import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.Car;
import mysqlconnection.tables.CarModel;
import mysqlconnection.tables.CarType;
import mysqlconnection.tables.FuelType;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
            pstmt.setInt(1, car.getModelId());
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

    // delete by ID
    public void deleteCarById(int carId) {
        String query = "DELETE FROM car WHERE car_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);

            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Car deleted successfully.");
            } else {
                System.out.println("Car with ID " + carId + " not found.");
            }
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
        SystemMessages.printYellowBoldText("CREATE CAR\n");
        Car car = userTypesCar();
        insertCarIntoDatabase(car);
    }

    // Prompt user for car information
    private Car userTypesCar() {
        CarModelModification carModelModification = new CarModelModification(mySqlConnection);
        MenuOption menuOption = new MenuOption();
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

        return new Car(carModel.getModelId(), fuelType, carType, registrationNumber, firstRegistrationDate, mileage);
    }


    public void updateCar() {
        SystemMessages.printYellowBoldText("UPDATE CAR\n");
        Scanner in = new Scanner(System.in);
        System.out.print("Enter car ID to update: ");
        int carId = in.nextInt();
        Car updatedCar = userTypesCar();

        updateCarById(carId, updatedCar);
    }

    public void updateCarById(int carId, Car updatedCar) {
        String query = "UPDATE car SET model_id = ?, fuel_type_name = ?, car_type_name = ?, registration_number = ?, first_registration_date = ?, mileage = ? WHERE car_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, updatedCar.getModelId());
            preparedStatement.setString(2, updatedCar.getFuelType().name());
            preparedStatement.setString(3, updatedCar.getCarType().name());
            preparedStatement.setString(4, updatedCar.getRegistrationNumber());
            preparedStatement.setDate(5, Date.valueOf(updatedCar.getFirstRegistrationDate()));
            preparedStatement.setInt(6, updatedCar.getMileage());
            preparedStatement.setInt(7, carId);

            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                System.out.println("Car updated successfully.");
            } else {
                System.out.println("Car with ID " + carId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

