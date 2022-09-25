package springbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

    private final JdbcContext jdbcContext;
    private final DataSource dataSource;

    public UserDao(final JdbcContext jdbcContext, final DataSource dataSource) {
        this.jdbcContext = jdbcContext;
        this.dataSource = dataSource;
    }

    public void add(final User user) throws SQLException {
        jdbcContext.workWithStatementStrategy((final Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into users(id, name, password) values(?, ?, ?)");

            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());

            return statement;
        });
    }

    public void deleteAll() throws SQLException {
        jdbcContext.workWithStatementStrategy((final Connection connection) ->
                connection.prepareStatement("delete from users"));
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select count(*) from users")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);

                resultSet.close();
                statement.close();
                connection.close();

                return count;
            }
        }
    }
}
