package springbook;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnector {

    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
