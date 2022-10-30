package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Member;
import springbook.domain.Workspace;

@Repository
public class JdbcWorkspace {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkspace(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Workspace> workspaces) {
        final String sql = "INSERT INTO workspace "
                + "(slack_id, bot_token, bot_slack_id) "
                + "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Workspace workspace = workspaces.get(i);

                ps.setString(1, workspace.getSlackId());
                ps.setString(2, workspace.getBotToken());
                ps.setString(3, workspace.getBotSlackId());
            }

            @Override
            public int getBatchSize() {
                return workspaces.size();
            }
        });
    }
}
