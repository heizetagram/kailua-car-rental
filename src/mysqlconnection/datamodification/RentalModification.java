package mysqlconnection.datamodification;

import infoprinter.InfoPrinter;
import menu.Menu;
import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.*;
import mysqlconnection.tables.customtables.CarWithCarModel;
import mysqlconnection.tables.customtables.RentalWithFullInfo;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RentalModification {
    private MySqlConnection mySqlConnection;
    private Connection connection;

    public RentalModification(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
        connection = mySqlConnection.getConnection();
    }

    // Create rental
    public void createRental() {
        SystemMessages.printBoldCyanText("\nCREATE RENTAL\n");
        Rental rental = userTypesRental();
        if (rental != null) {
            insertRentalToDatabase(rental);
        }
    }

    // Update rental
    public void updateRental() {
        SystemMessages.printBoldYellowText("\nEDIT CUSTOMER\n");
        String firstName = UI.promptFirstName();
        String lastName = UI.promptLastName();
        editRentalByCustomerName(firstName, lastName);
    }

    // Delete rental
    public void deleteRental() {
        SystemMessages.printBoldYellowText("\nDELETE RENTAL\n");
        String firstName = UI.promptFirstName();
        String lastName = UI.promptLastName();
        deleteRentalByCustomerName(firstName, lastName);
    }

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    // Edit rental by customer name
    private void editRentalByCustomerName(String firstName, String lastName) {
        int rentalId = findRentalIdByCustomerName(firstName, lastName);
        Rental rental = getRentalById(rentalId);
        Rental updatedRental;

        if (rental != null) {
            UI.promptString(); // Scanner bug
            SystemMessages.printBoldYellowText("\nSelect New Customer: \n");
            updatedRental = userTypesRental();
            String query = "UPDATE rental SET customer_id = ?, car_id = ?, from_date = ?, to_date = ?, max_km = ?, current_km = ? WHERE rental_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (updatedRental != null) {
                    setPreparedStatementData(preparedStatement, updatedRental);
                    preparedStatement.setInt(7, rentalId);

                    int foundRental = preparedStatement.executeUpdate();
                    if (foundRental > 0) {
                        SystemMessages.printSuccess("Customer successfully edited\n");
                    } else {
                        printErrorFindingRentalId(rentalId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete rental by customer name
    private void deleteRentalByCustomerName(String firstName, String lastName) {
        int rentalId = findRentalIdByCustomerName(firstName, lastName);
        String query = "DELETE FROM rental WHERE rental_id = " + rentalId + ";";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int foundCustomer = preparedStatement.executeUpdate();
            if (foundCustomer > 0) {
                SystemMessages.printSuccess("Rental successfully deleted\n");
            } else {
                printErrorFindingRentalId(rentalId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prompt user for rental information
    private Rental userTypesRental() {
        CustomerModification customerModification = new CustomerModification(mySqlConnection);
        CarModification carModification = new CarModification(mySqlConnection);
        Menu menu = new Menu();

        SystemMessages.printYellowText("First Name: ");
        String firstName = UI.promptString();

        SystemMessages.printYellowText("Last Name: ");
        String lastName = UI.promptString();

        int customer_id = customerModification.findCustomerIdByFullName(firstName, lastName);
        if (customer_id == 0) {
            SystemMessages.printError("No customer found\n");
            return null;
        }

        SystemMessages.printYellowText("Car Type:");
        menu.showCarTypeOptions();
        CarType carType = menu.chooseCarType();

        ArrayList<CarWithCarModel> carsByType = carModification.findCarsByCarType(carType);
        int carId = getCarIdFromList(carsByType);
        UI.promptString(); // Scanner bug

        if (carId != 0) {
            String currentHour = getCurrentHour();
            String currentMinute = getCurrentMinute();

            SystemMessages.printYellowText("Rent from (DD-MM-YYYY): ");
            String fromDateString = UI.promptString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm");
            LocalDateTime fromDate = LocalDateTime.parse(fromDateString + ", " + currentHour + ":" + currentMinute, formatter);

            SystemMessages.printYellowText("Rent until (DD-MM-YYYY): ");
            String toDateString = UI.promptString();
            LocalDateTime toDate = LocalDateTime.parse(toDateString + ", " + currentHour + ":" + currentMinute, formatter);

            SystemMessages.printYellowText("Max km: ");
            int maxKm = UI.promptInt();

            SystemMessages.printYellowText("Current km: ");
            int currentKm = UI.promptInt();
            UI.promptString(); // Scanner bug

            return new Rental(customer_id, carId, fromDate, toDate, maxKm, currentKm);
        } else {
            SystemMessages.printError("No available " + carType.name() + " cars left\n\n");
            return null;
        }
    }

    private String getCurrentHour() {
        String currentHour = Integer.toString(LocalTime.now().getHour());
        if (currentHour.length() == 1) {
            currentHour = "0" + currentHour;
        }
        return currentHour;
    }

    private String getCurrentMinute() {
        String currentMinute = Integer.toString(LocalTime.now().getMinute());
        if (currentMinute.length() == 1) {
            currentMinute = "0" + currentMinute;
        }
        return currentMinute;
    }

    // Insert rental to database
    private void insertRentalToDatabase(Rental rental) {
        try {
            String query = "INSERT INTO rental (customer_id, car_id, from_date, to_date, max_km, current_km) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setPreparedStatementData(preparedStatement, rental);
            preparedStatement.executeUpdate();
            SystemMessages.printSuccess("Rental created successfully!\n");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    // Set prepared statement data for Rental
    private void setPreparedStatementData(PreparedStatement pstmt, Rental rental) {
        try {
            pstmt.setInt(1, rental.getCustomerId());
            pstmt.setInt(2, rental.getCarId());
            pstmt.setTimestamp(3, Timestamp.valueOf(rental.getFromDate()));
            pstmt.setTimestamp(4, Timestamp.valueOf(rental.getToDate()));
            pstmt.setInt(5, rental.getMaxKm());
            pstmt.setInt(6, rental.getCurrentKm());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if list is empty and return the Car ID
    private int getCarIdFromList(ArrayList<CarWithCarModel> carsByType) {
        if (!carsByType.isEmpty()) {
            printAllCarsByCarType(carsByType);
            SystemMessages.printYellowText("Select Car ID: ");
            return UI.promptInt();
        } else {
            return 0;
        }
    }

    // Find rental ID by customer name
    private int findRentalIdByCustomerName(String firstName, String lastName) {
        String query = getQueryFromNameCriteria(firstName, lastName);
        ArrayList<RentalWithFullInfo> rentalsFullInfoWithGivenCustomer = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                rentalsFullInfoWithGivenCustomer.add(getRentalFullInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getRentalIdFromRental(rentalsFullInfoWithGivenCustomer);
    }

    // Get query from name criteira
    private String getQueryFromNameCriteria(String firstName, String lastName) {
        if (lastName.isEmpty()) {
            return "SELECT cstm.first_name, cstm.last_name, cm.brand, cm.model, c.registration_number, r.* FROM rental r JOIN customer cstm USING (customer_id) JOIN car c USING (car_id) JOIN car_model cm USING (model_id) WHERE cstm.first_name = '" + firstName + "';";
        } else {
            return "SELECT cstm.first_name, cstm.last_name, cm.brand, cm.model, c.registration_number, r.* FROM rental r JOIN customer cstm USING (customer_id) JOIN car c USING (car_id) JOIN car_model cm USING (model_id) WHERE cstm.first_name = '" + firstName + "' AND cstm.last_name = '" + lastName + "';";
        }
    }

    // Check if list is empty and get Rental ID
    private int getRentalIdFromRental(ArrayList<RentalWithFullInfo> rentalsFullInfo) {
        if (!rentalsFullInfo.isEmpty()) {
            printRentalsWithFullInfo(rentalsFullInfo);
            SystemMessages.printYellowText("\nSelect Rental ID: ");
            return UI.promptInt();
        } else {
            return 0;
        }
    }

    // Get rental by Rental ID
    private Rental getRentalById(int rentalId) {
        String query = "SELECT * FROM rental WHERE rental_id = " + rentalId + ";";
        Rental rental = null;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            if (rs.next()) {
                rental = getRental(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rental;
    }

    // Get rental
    private Rental getRental(ResultSet rs) {
        Rental rental = null;
        try {
            rental = new Rental(rs.getInt("rental_id"), rs.getInt("customer_id"), rs.getInt("car_id"), rs.getTimestamp("from_date").toLocalDateTime(), rs.getTimestamp("to_date").toLocalDateTime(), rs.getInt("max_km"), rs.getInt("current_km"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rental;
    }

    // Print out rentals with full info
    private void printRentalsWithFullInfo(ArrayList<RentalWithFullInfo> rentalsWithFullInfo) {
        InfoPrinter infoPrinter = new InfoPrinter();
        for (RentalWithFullInfo rentalFullInfo : rentalsWithFullInfo) {
            infoPrinter.printRentalFullInfo(rentalFullInfo);
        }
    }

    // Get rentals with full info
    private ArrayList<RentalWithFullInfo> getRentalsWithFullInfoByCustomerId(int customerId) {
        String query = "SELECT cstm.first_name, cstm.last_name, cm.brand, cm.model, c.registration_number, r.* FROM rental r JOIN customer cstm USING (customer_id) JOIN car c USING (car_id) JOIN car_model cm USING (model_id) WHERE cstm.customer_id = ?;";
        ArrayList<RentalWithFullInfo> rentalsWithFullInfo = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rentalsWithFullInfo.add(getRentalFullInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalsWithFullInfo;
    }

    // Get Rental will full info from a result set
    public RentalWithFullInfo getRentalFullInfo(ResultSet rs) {
        RentalWithFullInfo rentalWithFullInfo = null;
        try {
            rentalWithFullInfo = new RentalWithFullInfo(rs.getInt("rental_id"), rs.getInt("customer_id"), rs.getInt("car_id"), rs.getTimestamp("from_date").toLocalDateTime(), rs.getTimestamp("to_date").toLocalDateTime(), rs.getInt("max_km"), rs.getInt("current_km"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("registration_number"), rs.getString("brand"), rs.getString("model"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalWithFullInfo;
    }

    // Print all cars by car type
    private void printAllCarsByCarType(ArrayList<CarWithCarModel> carsByType) {
        InfoPrinter infoPrinter = new InfoPrinter();
        for (CarWithCarModel fullCar : carsByType) {
            infoPrinter.printFullCarInfo(fullCar);
        }
    }

    // Print error finding rental id
    private void printErrorFindingRentalId(int rentalId) {
        if (rentalId == 0) {
            SystemMessages.printError("Customer could not be found\n");
        } else {
            SystemMessages.printError("Customer with ID: " + rentalId + " could not be found\n");
        }
    }
}
