package springbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GoogleUserDao extends UserDao {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook", "spring", "book"
        );
    }
}
