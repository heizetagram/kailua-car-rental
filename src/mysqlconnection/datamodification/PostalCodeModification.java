package mysqlconnection.datamodification;

import mysqlconnection.MySqlConnection;
import mysqlconnection.tables.PostalCode;
import ui.SystemMessages;
import ui.UI;

import java.sql.Connection;

public class PostalCodeModification {
    private MySqlConnection mySqlConnection;
    private Connection connection;

    public PostalCodeModification(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
        connection = mySqlConnection.getConnection();
    }

    public PostalCode createPostalCode() {
        PostalCode postalCode = null;
        return checkIfPostalCodeExists(postalCode);
    }
    
    private PostalCode userTypesPostalCode() {
        SystemMessages.printYellowText("Postal Code: ");
        int postalCode = UI.promptInt();
        return  null;
    }

    private PostalCode checkIfPostalCodeExists(PostalCode postalCode) {
        return null;
    }
}
