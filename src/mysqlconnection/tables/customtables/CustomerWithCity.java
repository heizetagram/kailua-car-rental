package mysqlconnection.tables.customtables;

import java.time.LocalDate;

public class CustomerWithCity {
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private int postalCode;
    private String city;
    private String mobilePhone;
    private String phoneNumber;
    private String email;
    private String licenseNumber;
    private LocalDate issueDate;

    public CustomerWithCity(int customerId, String firstName, String lastName, String address, int postalCode, String city, String mobilePhone, String phoneNumber, String email, String licenseNumber, LocalDate issueDate) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.mobilePhone = mobilePhone;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.licenseNumber = licenseNumber;
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

    public String getCity() {
        return city;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
}