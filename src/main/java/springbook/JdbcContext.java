package springbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import springbook.strategy.StatementStrategy;

public class JdbcContext {

    private final DataSource dataSource;

    public JdbcContext(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execute(final String sql) throws SQLException {
        workWithStatementStrategy((final Connection connection) ->
                connection.prepareStatement(sql));
    }

    public void workWithStatementStrategy(final StatementStrategy strategy) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = strategy.makeStatement(connection)) {
            statement.executeUpdate();
        }
    }
}
