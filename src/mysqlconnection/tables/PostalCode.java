package mysqlconnection.tables;

public class PostalCode {
    private int postalCode;
    private String city;

    public PostalCode(int postalCode, String city) {
        this.postalCode = postalCode;
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
