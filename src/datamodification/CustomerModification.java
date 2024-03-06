package datamodification;

import infoprinter.InfoPrinter;
import mysqlconnection.MySqlConnection;
import ui.UI;
import tables.Customer;

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
        Customer customer = userTypesCustomer();
        insertCustomerToDatabase(customer);
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

            System.out.println("Customer added successfully");
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
            if (foundCustomer == 1) {
                System.out.println("Customer deleted");
            } else {
                System.out.println("Could not find customer");
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

    private Customer userTypesCustomer() {
        System.out.println("CREATE CUSTOMER");
        System.out.print("First Name: ");
        String firstName = UI.promptString();

        System.out.print("Last Name: ");
        String lastName = UI.promptString();

        System.out.print("Address: ");
        String address = UI.promptString();

        System.out.print("Postal Code: ");
        int postalCode = UI.promptInt();
        UI.promptString();

        System.out.print("Mobile Phone: ");
        String mobilePhone = UI.promptString();

        System.out.print("Phone Number: ");
        String phoneNumber = UI.promptString();

        System.out.print("E-mail: ");
        String email = UI.promptString();

        System.out.print("License Number: ");
        String licenseNumber = UI.promptString();

        System.out.print("Issue Date (dd-MM-yyyy): ");
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
