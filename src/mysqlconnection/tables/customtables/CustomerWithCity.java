package mysqlconnection.tables.customtables;

import mysqlconnection.tables.Customer;

import java.time.LocalDate;

public class CustomerWithCity extends Customer  {
    private String city;

    public CustomerWithCity(int customerId, String firstName, String lastName, String address, int postalCode, String city, String mobilePhone, String phoneNumber, String email, String licenseNumber, LocalDate issueDate) {
        super(customerId, firstName, lastName, address, postalCode, mobilePhone, phoneNumber, email, licenseNumber, issueDate);
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}