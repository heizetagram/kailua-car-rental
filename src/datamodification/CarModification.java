package datamodification;

import mysqlconnection.MySqlConnection;
import tables.Car;
import tables.CarModel;
import tables.CarType;
import tables.FuelType;
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
    public CarModel getCarModelByBrandAndModel(String brand, String model) {
        String query = "SELECT * FROM car_model WHERE brand = ? AND model = ?";
        CarModel carModel = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int modelId = rs.getInt("model_id");
                carModel = new CarModel(modelId, brand, model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carModel;
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

    public void createCar() {
        Car car = userTypesCar();
        insertCarIntoDatabase(car);
    }

    private Car userTypesCar() {
        CarModelModification carModelModification = new CarModelModification(mySqlConnection);
        System.out.println("CREATE CAR");
        CarModel carModel = carModelModification.createCarModel();

        System.out.print("Fuel Type: ");
        FuelType fuelType = FuelType.valueOf(UI.promptString());

        System.out.print("Car Type: ");
        CarType carType = CarType.valueOfIgnoreCase(UI.promptString());

        System.out.print("Registration Number: ");
        String registrationNumber = UI.promptString();

        System.out.print("First Registration Date: ");
        String dateString = UI.promptString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate firstRegistrationDate = LocalDate.parse(dateString, formatter);

        System.out.print("Mileage: ");
        int mileage = UI.promptInt();
        UI.promptString(); // Scanner bug

        return new Car(carModel, fuelType, carType, registrationNumber, firstRegistrationDate, mileage);
    }
}
