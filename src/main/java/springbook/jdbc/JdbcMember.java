package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Member;

@Repository
public class JdbcMember {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMember(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Member> members) {
        final String sql = "INSERT INTO member "
                + "(first_login, slack_id, thumbnail_url, username, token, workspace_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Member member = members.get(i);

                ps.setBoolean(1, member.isFirstLogin());
                ps.setString(2, member.getSlackId());
                ps.setString(3, member.getThumbnailUrl());
                ps.setString(4, member.getUsername());
                ps.setString(5, member.getToken());
                ps.setLong(6, member.getWorkspaceId());
            }

            @Override
            public int getBatchSize() {
                return members.size();
            }
        });
    }
}
