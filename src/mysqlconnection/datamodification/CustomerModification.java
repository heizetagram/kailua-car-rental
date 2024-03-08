package mysqlconnection.datamodification;

import infoprinter.InfoPrinter;
import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.PostalCode;
import ui.SystemMessages;
import ui.UI;
import mysqlconnection.tables.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author David
 */

public class CustomerModification {
    private Connection connection;

    public CustomerModification(MySqlConnection mySqlConnection) {
        connection = mySqlConnection.getConnection();
    }

    // Create customer
    public void createCustomer() {
        SystemMessages.printBoldYellowText("\nADD CUSTOMER\n");
        Customer customer = userTypesCustomer();
        insertCustomerToDatabase(customer);
    }

    // Update customer
    public void updateCustomer() {
        SystemMessages.printBoldYellowText("\nEDIT CUSTOMER\n");
        String firstName = UI.promptFirstName();
        String lastName = UI.promptLastName();
        editCustomerByFullName(firstName, lastName);
    }

    // Delete customer
    public void deleteCustomer() {
        SystemMessages.printBoldYellowText("\nDELETE CUSTOMER\n");
        String firstName = UI.promptFirstName();
        String lastName = UI.promptLastName();
        deleteCustomerByFullName(firstName, lastName);
    }

    // Insert customer to database
    private void insertCustomerToDatabase(Customer customer) {
        try {
            String query = "INSERT INTO customer (first_name, last_name, address, postal_code, mobile_phone, phone_number, email, license_number, issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setPreparedStatementData(preparedStatement, customer);
            preparedStatement.executeUpdate();
            SystemMessages.printSuccess("Customer added successfully!\n");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    // Get customer by customer_id
    public Customer getCustomerById(int customerId) {
        String query = "SELECT * FROM customer WHERE customer_id = " + customerId + ";";
        Customer customer = null;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            if (rs.getInt("customer_id") == customerId) {
                customer = getCustomer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Delete customer by full name
    private void deleteCustomerByFullName(String firstName, String lastName) {
        int customerId = findCustomerIdByFullName(firstName, lastName);
        String query = "DELETE FROM customer WHERE customer_id = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            int foundCustomer = pstmt.executeUpdate();
            if (foundCustomer > 0) {
                SystemMessages.printSuccess("Customer successfully deleted\n");
            } else {
                printErrorFindingCustomerId(customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit customer by full name
    private void editCustomerByFullName(String firstName, String lastName) {
        int customerId = findCustomerIdByFullName(firstName, lastName);
        Customer customer = getCustomerById(customerId);
        Customer updatedCustomer;

        if (customer != null) {
            UI.promptString(); // Scanner bug
            updatedCustomer = userTypesCustomer();
            String query = "UPDATE customer SET first_name =  ?, last_name = ?, address = ?, postal_code = ?, mobile_phone = ?, phone_number = ?, email = ?, license_number = ?, issue_date = ? WHERE customer_id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setPreparedStatementData(preparedStatement, updatedCustomer);
                preparedStatement.setInt(10, customerId);

                int foundCustomer = preparedStatement.executeUpdate();
                if (foundCustomer > 0) {
                    SystemMessages.printSuccess("Customer successfully edited\n");
                } else {
                    printErrorFindingCustomerId(customerId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SystemMessages.printError("Customer does not exist\n");
            UI.promptString(); // Scanner bug but bad placement
        }
    }

    // Set prepared statement data for Customer
    private void setPreparedStatementData(PreparedStatement pstmt, Customer customer) {
        try {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.getPostalCode());
            pstmt.setString(5, customer.getMobilePhone());
            pstmt.setString(6, customer.getPhoneNumber());
            pstmt.setString(7, customer.getEmail());
            pstmt.setString(8, customer.getLicense_number());
            pstmt.setDate(9, Date.valueOf(customer.getIssueDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find customer_id by full name
    public int findCustomerIdByFullName(String firstName, String lastName) {
        String query = getQueryFromNameCriteria(firstName, lastName);
        ArrayList<Customer> customersWithGivenName = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                customersWithGivenName.add(getCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getCustomerIdFromList(customersWithGivenName);
    }

    // Check if list is empty and get Customer ID
    private int getCustomerIdFromList(ArrayList<Customer> customers) {
        if (!customers.isEmpty()) {
            printCustomers(customers);
            SystemMessages.printYellowText("Select Customer ID: ");
            return UI.promptInt();
        } else {
            return 0;
        }
    }

    // Check if user has provided firstName and lastName
    private String getQueryFromNameCriteria(String firstName, String lastName) {
        if (lastName.isEmpty()) {
            return "SELECT *, pc.city FROM customer JOIN postal_code pc USING (postal_code) WHERE first_name = '" + firstName + "';";
        } else {
            return "SELECT *, pc.city FROM customer JOIN postal_code pc USING (postal_code) WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';";
        }
    }

    // Print out customers
    private void printCustomers(ArrayList<Customer> customers) {
        InfoPrinter infoPrinter = new InfoPrinter();
        for (Customer customer : customers) {
            infoPrinter.printCustomer(customer);
        }
    }

    // Prompt user for customer information
    private Customer userTypesCustomer() {
        PostalCodeModification postalCodeModification = new PostalCodeModification(new MySqlConnection());

        SystemMessages.printYellowText("First Name: ");
        String firstName = UI.promptString();

        SystemMessages.printYellowText("Last Name: ");
        String lastName = UI.promptString();

        SystemMessages.printYellowText("Address: ");
        String address = UI.promptString();

        //Postal code
        PostalCode postalCode = postalCodeModification.createPostalCode();

        SystemMessages.printYellowText("Mobile Phone: ");
        String mobilePhone = UI.promptString();

        SystemMessages.printYellowText("Phone Number: ");
        String phoneNumber = UI.promptString();

        SystemMessages.printYellowText("E-mail: ");
        String email = UI.promptString();

        SystemMessages.printYellowText("License Number: ");
        String licenseNumber = UI.promptString();

        SystemMessages.printYellowText("Issue Date (DD-MM-YYYY): ");
        String issueDateString = UI.promptString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate issueDate = LocalDate.parse(issueDateString, formatter);
        return new Customer(firstName, lastName, address, postalCode.getPostalCode(), mobilePhone, phoneNumber, email, licenseNumber, issueDate);
    }

    // Get customer
    private Customer getCustomer(ResultSet rs) {
        Customer customer = null;
        try {
            customer = new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("address"), rs.getInt("postal_code"), rs.getString("mobile_phone"), rs.getString("phone_number"), rs.getString("email"), rs.getString("license_number"), rs.getDate("issue_date").toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Print error finding customer id
    private void printErrorFindingCustomerId(int customerId) {
        if (customerId == 0) {
            SystemMessages.printError("Customer could not be found\n");
        } else {
            SystemMessages.printError("Customer with ID: " + customerId + " could not be found\n");
        }
    }
}
