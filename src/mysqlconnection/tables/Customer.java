package mysqlconnection.tables;

import java.time.LocalDate;

/**
 * @author David
 */

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private int postalCode;
    private String mobilePhone;
    private String phoneNumber;
    private String email;
    private String license_number;
    private LocalDate issueDate;

    public Customer(int customerId, String firstName, String lastName, String address, int postalCode, String mobilePhone, String phoneNumber, String email, String license_number, LocalDate issueDate) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.mobilePhone = mobilePhone;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.license_number = license_number;
        this.issueDate = issueDate;
    }

    public Customer(String firstName, String lastName, String address, int postalCode, String mobilePhone, String phoneNumber, String email, String license_number, LocalDate issueDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.mobilePhone = mobilePhone;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.license_number = license_number;
        this.issueDate = issueDate;
    }


    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getLicense_number() {
        return license_number;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
}
