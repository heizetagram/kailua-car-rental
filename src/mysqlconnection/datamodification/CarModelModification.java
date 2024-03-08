package mysqlconnection.datamodification;

import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.CarModel;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;

/**
 * @author David
 */

public class CarModelModification {
    private Connection connection;

    public CarModelModification(MySqlConnection mySqlConnection) {
        connection = mySqlConnection.getConnection();
    }

    // Create car model
    public CarModel createCarModel() {
        CarModel carModel = userTypesCarModel();
        return checkIfCarModelExists(carModel);
    }

    // User types car model
    private CarModel userTypesCarModel() {
        SystemMessages.printYellowText("Brand: ");
        String brand = UI.promptString();

        SystemMessages.printYellowText("Model: ");
        String model = UI.promptString();

        return new CarModel(brand, model);
    }

    // Insert car model into database
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

    // Get car model by brand and name
    private CarModel getCarModelByBrandAndName(String brand, String model) {
        String query = "SELECT * FROM car_model WHERE brand = '" + brand + "' AND model = '" + model +"';";
        CarModel carModel  = null;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                carModel = getCarModelFromRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }

    // Check if car model exists
    private CarModel checkIfCarModelExists(CarModel carModel) {
        String query = "SELECT * FROM car_model WHERE brand = '" + carModel.getBrand() + "' AND model = '" + carModel.getModel() + "';";
        CarModel updatedCarModel = null;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Checks if brand/model already exists
            if (!rs.next()) {
                insertCarModelIntoDatabase(carModel);
                updatedCarModel = getCarModelByBrandAndName(carModel.getBrand(), carModel.getModel());
            } else { // If it already exists, then return the car_model of the result set
                updatedCarModel = new CarModel(rs.getInt("model_id"), rs.getString("brand"), rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedCarModel;
    }

    // Get car model
    private CarModel getCarModelFromRs(ResultSet rs) {
        CarModel carModel = null;
        try {
            carModel = new CarModel(rs.getInt("model_id"), rs.getString("brand"), rs.getString("model"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }
}
