package mysqlconnection.tables;

public class PostalCode {
    private int postalCode;
    private String city;

    public PostalCode(int postalCode, String city) {
        this.postalCode = postalCode;
        this.city = city;
    }

    public PostalCode(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
