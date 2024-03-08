package mysqlconnection.datamodification;

import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.PostalCode;
import ui.SystemMessages;
import ui.UI;

import java.sql.*;

public class PostalCodeModification {
    private Connection connection;

    public PostalCodeModification(MySqlConnection mySqlConnection) {
        connection = mySqlConnection.getConnection();
    }

    // Create postal code
    public PostalCode createPostalCode() {
        PostalCode postalCode = userTypesPostalCode();
        return checkIfPostalCodeExists(postalCode);
    }

    // User types postal code
    private PostalCode userTypesPostalCode() {
        SystemMessages.printYellowText("Postal Code: ");
        int postalCode = UI.promptInt();
        UI.promptString(); // Scanner bug

        SystemMessages.printYellowText("City: ");
        String city = UI.promptString();

        return new PostalCode(postalCode, city);
    }

    // Check if postal code already exists
    private PostalCode checkIfPostalCodeExists(PostalCode postalCode) {
        String query = "SELECT * FROM postal_code WHERE postal_code = ?;";
        PostalCode updatedPostalCode = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, postalCode.getPostalCode());
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                insertPostalCodeIntoDatabase(postalCode);
                updatedPostalCode = getPostalCodeByPk(postalCode.getPostalCode());
            } else {
                updatedPostalCode = new PostalCode(rs.getInt("postal_code"), rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedPostalCode;
    }

    // Insert postal code into database
    private void insertPostalCodeIntoDatabase(PostalCode postalCode) {
        try {
            String query = "INSERT INTO postal_code (postal_code, city) VALUES (?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, postalCode.getPostalCode());
            pstmt.setString(2, postalCode.getCity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get postal code my postal code primary key
    private PostalCode getPostalCodeByPk(int postalCode) {
        String query = "SELECT * FROM postal_code WHERE postal_code = " + postalCode + ";";
        PostalCode foundPostalCode = null;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            if (rs.next()) {
                foundPostalCode = getPostalCode(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundPostalCode;
    }

    // Get postal code
    private PostalCode getPostalCode(ResultSet rs) {
        PostalCode postalCode = null;
        try {
            postalCode = new PostalCode(rs.getInt("postal_code"), rs.getString("city"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postalCode;
    }
}
