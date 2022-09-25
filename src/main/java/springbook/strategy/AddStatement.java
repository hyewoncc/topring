package springbook.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import springbook.User;

public class AddStatement implements StatementStrategy {

    private final User user;

    public AddStatement(final User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makeStatement(final Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");

        statement.setString(1, user.getId());
        statement.setString(2, user.getName());
        statement.setString(3, user.getPassword());

        return statement;
    }
}
