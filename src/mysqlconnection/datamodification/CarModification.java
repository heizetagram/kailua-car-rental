package mysqlconnection.datamodification;

import menu.Menu;
import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.*;
import mysqlconnection.tables.customtables.CarWithCarModel;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


    // Create car
    public void createCar() {
        SystemMessages.printBoldYellowText("\nADD CAR\n");
        Car car = userTypesCar();
        insertCarIntoDatabase(car);
    }

    // Update car
    public void updateCar() {
        SystemMessages.printBoldYellowText("\nUPDATE CAR\n");
        Scanner in = new Scanner(System.in);
        System.out.print("Enter car ID to update: ");
        int carId = in.nextInt();
        Car updatedCar = userTypesCar();

        updateCarById(carId, updatedCar);
    }

    // Delete car
    public void deleteCar() {
        SystemMessages.printBoldYellowText("\nDELETE CAR\n");
        Scanner in = new Scanner(System.in);
        System.out.print("Enter car ID to delete: ");
        int carId = in.nextInt();

        deleteCarById(carId);
    }

    // Insert car into database
    private void insertCarIntoDatabase(Car car) {
        try {
            String query = "INSERT INTO car (model_id, fuel_type_name, car_type_name, registration_number, first_registration_date, mileage) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            setPreparedStatementData(pstmt, car);
            pstmt.executeUpdate();
            SystemMessages.printSuccess("Successfully added car");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete car by ID
    private void deleteCarById(int carId) {
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

    // Prompt user for car information
    private Car userTypesCar() {
        CarModelModification carModelModification = new CarModelModification(mySqlConnection);
        Menu menuOption = new Menu();
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

    // Update car by ID
    public void updateCarById(int carId, Car updatedCar) {
        String query = "UPDATE car SET model_id = ?, fuel_type_name = ?, car_type_name = ?, registration_number = ?, first_registration_date = ?, mileage = ? WHERE car_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            setPreparedStatementData(pstmt, updatedCar);
            pstmt.setInt(7, carId);

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Car updated successfully.");
            } else {
                System.out.println("Car with ID " + carId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Set prepared statement data
    private void setPreparedStatementData(PreparedStatement pstmt, Car car) {
        try {
            pstmt.setInt(1, car.getModelId());
            pstmt.setString(2, car.getFuelType().name());
            pstmt.setString(3, car.getCarType().name());
            pstmt.setString(4, car.getRegistrationNumber());
            pstmt.setDate(5, Date.valueOf(car.getFirstRegistrationDate()));
            pstmt.setInt(6, car.getMileage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find cars by car type
    public ArrayList<CarWithCarModel> findCarsByCarType(CarType typeName) {
        String query = "SELECT c.*, cm.brand, cm.model FROM car c JOIN car_model cm USING (model_id) WHERE car_type_name = ?";
        ArrayList<CarWithCarModel> carsByType = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, typeName.toString());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                carsByType.add(getCarWithCarModel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carsByType;
    }

    // Get car
    private Car getCar(ResultSet rs) {
        Car car = null;
        try {
            car = new Car(rs.getInt("car_id"), rs.getInt("model_id"), FuelType.valueOf(rs.getString("fuel_type_name")), CarType.valueOf(rs.getString("car_type_name")), rs.getString("registration_number"), rs.getDate("first_registration_date").toLocalDate(), rs.getInt("mileage"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    // Get car and car model
    private CarWithCarModel getCarWithCarModel(ResultSet rs) {
        CarWithCarModel fullCar = null;
        try {
            fullCar = new CarWithCarModel(rs.getInt("car_id"), rs.getInt("model_id"), FuelType.valueOf(rs.getString("fuel_type_name")), CarType.valueOf(rs.getString("car_type_name")), rs.getString("registration_number"), rs.getDate("first_registration_date").toLocalDate(), rs.getInt("mileage"), rs.getString("brand"), rs.getString("model"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullCar;
    }
}

