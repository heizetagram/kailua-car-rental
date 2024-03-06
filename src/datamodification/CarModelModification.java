package datamodification;

import mysqlconnection.MySqlConnection;
import tables.CarModel;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;

/**
 * @author David
 */

public class CarModelModification {
    private MySqlConnection mySqlConnection;
    private Connection connection;

    public CarModelModification(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
        connection = mySqlConnection.getConnection();
    }

    public CarModel createCarModel() {
        CarModel carModel = userTypesCarModel();
        return checkIfCarModelExists(carModel);
    }

    private CarModel userTypesCarModel() {
        SystemMessages.printYellowText("Brand: ");
        String brand = UI.promptString();

        SystemMessages.printYellowText("Model: ");
        String model = UI.promptString();

        return new CarModel(brand, model);
    }

    private void insertCarModelIntoDatabase(CarModel carModel) {
        try {
            String query = "INSERT INTO car_model (brand, model) VALUES (?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, carModel.getBrand());
            pstmt.setString(2, carModel.getModel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public CarModel getCarModelById(int model_id) {
        CarModel carModel = null;
        String query = "SELECT * FROM car_model WHERE model_id = " + carModel.getModelId() + ";";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                carModel = new CarModel(rs.getString("brand"), rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }
     */

    private CarModel checkIfCarModelExists(CarModel carModel) {
        String query = "SELECT * FROM car_model WHERE brand = '" + carModel.getBrand() + "' AND model = '" + carModel.getModel() + "';";
        CarModel updatedCarModel = null;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Checks if brand/model already exists
            if (!rs.next()) {
                insertCarModelIntoDatabase(carModel);
                updatedCarModel = carModel;
            } else { // If it already exists, then return the car_model of the result set
                updatedCarModel = new CarModel(rs.getInt("model_id"), rs.getString("brand"), rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedCarModel;
    }

}
