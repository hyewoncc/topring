package springbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/springbook";
    public static final String MYSQL_USER = "spring";
    public static final String MYSQL_PASSWORD = "book";

    public void add(final User user) throws ClassNotFoundException, SQLException {
        Class.forName(MYSQL_JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(
                MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD
        );

        PreparedStatement statement = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)"
        );
        statement.setString(1, user.getId());
        statement.setString(2, user.getName());
        statement.setString(3, user.getPassword());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public User get(final String id) throws ClassNotFoundException, SQLException {
        Class.forName(MYSQL_JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(
                MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD
        );

        PreparedStatement statement = connection.prepareStatement(
                "select id, name, password from users where id = ?"
        );
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        statement.close();
        connection.close();

        return user;
    }
}
