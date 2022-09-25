package springbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

@Controller
public class UserDao {

    private DataSource dataSource;

    public void add(final User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into users(id, name, password) values(?, ?, ?)")) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User get(final String id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select id, name, password from users where id = ?")) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));

                return user;
            } catch (SQLException e) {
                throw new EmptyResultDataAccessException(1);
            }
        }
    }

    public int getCount() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "select count(*) from users"
        );
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        resultSet.close();
        statement.close();
        connection.close();

        return count;
    }

    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from users")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
