package springbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DBConnector dbConnector;

    public UserDao() {
        this.dbConnector = new GoogleDBConnector();
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {
        Connection connection = dbConnector.makeConnection();

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
        Connection connection = dbConnector.makeConnection();

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
