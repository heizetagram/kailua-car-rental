package mysqlconnection;

import java.sql.*;

/**
 * @author Freddy
 */

public class TestConnection {
    public static void main(String[] args) {
        String query = "SELECT * FROM customer LIMIT 5;";
        String database = "jdbc:mysql://127.0.0.1:3306/kailua_car_rental";
        String username = "root";
        String password = "guest";
        try {
            Connection con = DriverManager.getConnection(database, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                System.out.println(rs.getInt("customer_id") + ": " + rs.getString("first_name"));
            con.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
