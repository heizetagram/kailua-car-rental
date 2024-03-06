package mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Pat
 */

// Insert information
public class MySqlConnection {
  private String database = "jdbc:mysql://127.0.0.1:3306/kailua_car_rental";
  private String username = "root";
  private String password = "123";
  private Connection connection = null;

  public MySqlConnection() {
    createConnection();
  }

  // Create connection
  private void createConnection() {
    if (connection != null)
      return; // If connection already created, just return that to ensure singleton

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(database, username, password);
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }
  }

  // Close connection
  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("EXCEPTION: " + e.getStackTrace());
    }
    connection = null;
  }

  // Get connection
  public Connection getConnection() {
    return connection;
  }
}
