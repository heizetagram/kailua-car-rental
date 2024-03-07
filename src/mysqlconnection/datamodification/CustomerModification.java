package mysqlconnection.datamodification;

import infoprinter.InfoPrinter;
import menu.inputcriteria.InputCriteria;
import mysqlconnection.MySqlConnection;
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

    // Create customer by given criteria
    public void createCustomer() {
        SystemMessages.printYellowBoldText("CREATE CUSTOMER\n");
        Customer customer = userTypesCustomer();
        insertCustomerToDatabase(customer);
    }

    public void editCustomer() {
        SystemMessages.printYellowBoldText("EDIT CUSTOMER\n");
        String firstName = UI.promptString();
        String lastName = UI.promptString();
        editCustomerByFullName(firstName, lastName);
    }

    // Add customer
    private void insertCustomerToDatabase(Customer customer) {
        try {
            String query = "INSERT INTO customer (first_name, last_name, address, postal_code, mobile_phone, phone_number, email, license_number, issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getMobilePhone());
            preparedStatement.setString(6, customer.getPhoneNumber());
            preparedStatement.setString(7, customer.getEmail());
            preparedStatement.setString(8, customer.getLicense_number());
            preparedStatement.setDate(9, Date.valueOf(customer.getIssueDate()));

            preparedStatement.executeUpdate();
            SystemMessages.printSuccess("Customer added successfully!");
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
            while (rs.next()) {
                if (rs.getInt("customer_id") == customerId) {
                    customer = getCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Delete customer by full name
    public void deleteCustomerByFullName(String firstName, String lastName) {
        int customerId = findCustomerIdByFullName(firstName, lastName);
        String query = "DELETE FROM customer WHERE customer_id = " + customerId + ";";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int foundCustomer = preparedStatement.executeUpdate();
            if (foundCustomer > 0) {
                SystemMessages.printSuccess("Customer successfully deleted\n");
            } else {
                SystemMessages.printError("Customer with ID: " + customerId + " could not be found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit customer by full name
    private void editCustomerByFullName(String firstName, String lastName) {
        int customerId = findCustomerIdByFullName(firstName, lastName);
        Customer customer = getCustomerById(customerId);
        UI.promptString(); // Scanner bug
        Customer updatedCustomer = userTypesCustomer();

        String query = "UPDATE customer SET first_name =  ?, last_name = ?, address = ?, postal_code = ?, mobile_phone = ?, phone_number = ?, email = ?, license_number = ?, issue_date = ? WHERE customer_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedCustomer.getLastName());
            preparedStatement.setString(2, updatedCustomer.getAddress());
            preparedStatement.setInt(3, updatedCustomer.getPostalCode());
            preparedStatement.setString(4, updatedCustomer.getMobilePhone());
            preparedStatement.setString(5, updatedCustomer.getPhoneNumber());
            preparedStatement.setString(6, updatedCustomer.getEmail());
            preparedStatement.setString(7, updatedCustomer.getLicense_number());
            preparedStatement.setDate(8, Date.valueOf(updatedCustomer.getIssueDate()));
            preparedStatement.setInt(9, customerId);
            int foundCustomer = preparedStatement.executeUpdate();

            if (foundCustomer > 0) {
                SystemMessages.printSuccess("Customer successfully edited\n");
            } else {
                SystemMessages.printError("Customer with ID: " + customerId + " could not be found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find customer_id by full name
    private int findCustomerIdByFullName(String firstName, String lastName) {
        String query = getQueryFromNameCriteria(firstName, lastName);
        ArrayList<Customer> customersWithGivenName = new ArrayList<>();
        int chosenId = 0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                customersWithGivenName.add(getCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkIfListIsEmpty(customersWithGivenName);
    }

    private int checkIfListIsEmpty(ArrayList<Customer> customers) {
        if (!customers.isEmpty()) {
            printCustomers(customers);
            System.out.print("Choose Customer ID: ");
            return UI.promptInt();
        } else {
            return 0;
        }
    }

    // Check if user has provided firstName and lastName
    private String getQueryFromNameCriteria(String firstName, String lastName) {
        if (lastName.isEmpty()) {
            return "SELECT *, pc.city FROM customer JOIN postal_code pc USING postal_code WHERE first_name = '" + firstName + "';";
        } else {
           return "SELECT *, pc.city FROM customer JOIN postal_code pc USING (postal_code) WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';";
        }
    }

    // Prints out customers
    private void printCustomers(ArrayList<Customer> customers) {
        InfoPrinter infoPrinter = new InfoPrinter();
        for (Customer customer : customers) {
            infoPrinter.printCustomer(customer);
        }
    }

    // Prompt user for customer information
    private Customer userTypesCustomer() {
        InputCriteria inputCriteria = new InputCriteria();

        SystemMessages.printYellowText("First Name: ");
        String firstName = UI.promptString();

        SystemMessages.printYellowText("Last Name: ");
        String lastName = UI.promptString();

        SystemMessages.printYellowText("Address: ");
        String address = UI.promptString();

        SystemMessages.printYellowText("Postal Code: ");
        int postalCode = UI.promptInt();
        UI.promptString();

        SystemMessages.printYellowText("Mobile Phone: ");
        String mobilePhone = UI.promptString();

        SystemMessages.printYellowText("Phone Number: ");
        String phoneNumber = UI.promptString();

        SystemMessages.printYellowText("E-mail: ");
        String email = UI.promptString();

        SystemMessages.printYellowText("License Number: ");
        String licenseNumber = UI.promptString();

        SystemMessages.printYellowText("Issue Date (dd-MM-yyyy): ");
        String issueDateString = UI.promptString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate issueDate = LocalDate.parse(issueDateString, formatter);
        System.out.println();

        return new Customer(firstName, lastName, address, postalCode, mobilePhone, phoneNumber, email, licenseNumber, issueDate);
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
}
