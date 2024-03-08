package mysqlconnection.datamodification;

import infoprinter.InfoPrinter;
import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.customtables.CarWithCarModel;
import mysqlconnection.tables.customtables.CustomerWithCity;
import mysqlconnection.tables.customtables.RentalWithFullInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisplayModification {
    private MySqlConnection mySqlConnection;
    private Connection connection;
    private InfoPrinter infoPrinter;

    public DisplayModification(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
        connection = mySqlConnection.getConnection();
        infoPrinter = new InfoPrinter();
    }

    // Print all cars
    public void printAllCars() {
        ArrayList<CarWithCarModel> carsAndCarModels = getCarsWithCarModels();
        for (CarWithCarModel carAndCarModel : carsAndCarModels) {
            infoPrinter.printFullCarInfo(carAndCarModel);
        }
    }

    // Print all customers
    public void printAllCustomers() {
        ArrayList<CustomerWithCity> customersWithCity = getCustomersWithCity();
        for (CustomerWithCity customerWithCity : customersWithCity) {
            infoPrinter.printCustomerWithCity(customerWithCity);
        }
    }

    // Print all rentals
    public void printAllRentals() {
        ArrayList<RentalWithFullInfo> rentalsWithFullInfo = getRentalsWithFullInfo();
        for (RentalWithFullInfo rentalFullInfo : rentalsWithFullInfo) {
            infoPrinter.printRentalFullInfo(rentalFullInfo);
        }

    }

    ////////////////////////////////////////////

    // Get cars with car models
    private ArrayList<CarWithCarModel> getCarsWithCarModels() {
        CarModification carModification = new CarModification(mySqlConnection);
        String query = "SELECT c.*, cm.brand, cm.model FROM car c JOIN car_model cm USING (model_id) ORDER BY cm.brand;";
        ArrayList<CarWithCarModel> carsAndCarModels = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                carsAndCarModels.add(carModification.getCarWithCarModelFromRs(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carsAndCarModels;
    }

    // Get customers with city
    private ArrayList<CustomerWithCity> getCustomersWithCity() {
        String query = "SELECT c.customer_id, c.first_name, c.last_name, c.address, c.postal_code, pc.city, c.mobile_phone, c.phone_number, c.email, c.license_number, c.issue_date FROM customer c JOIN postal_code pc USING (postal_code);";
        ArrayList<CustomerWithCity> customersWithCity = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                customersWithCity.add(getCustomerWithCityFromRs(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersWithCity;
    }

    // Get rentals with full info
    private ArrayList<RentalWithFullInfo> getRentalsWithFullInfo() {
        RentalModification rentalModification = new RentalModification(mySqlConnection);
        String query = "SELECT cstm.first_name, cstm.last_name, cm.brand, cm.model, c.registration_number, r.* FROM rental r JOIN customer cstm USING (customer_id) JOIN car c USING (car_id) JOIN car_model cm USING (model_id);";
        ArrayList<RentalWithFullInfo> rentalsWithFullInfo = new ArrayList<>();


        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                rentalsWithFullInfo.add(rentalModification.getRentalWithFullInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rentalsWithFullInfo;
    }

    //////////////////////////////////

    // Get customer with city
    private CustomerWithCity getCustomerWithCityFromRs(ResultSet rs) {
        CustomerWithCity customerWithCity = null;
        try {
            customerWithCity = new CustomerWithCity(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("address"), rs.getInt("postal_code"), rs.getString("city"), rs.getString("mobile_phone"), rs.getString("phone_number"), rs.getString("email"), rs.getString("license_number"), rs.getDate("issue_date").toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerWithCity;
    }
}
