package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Channel;

@Repository
public class JdbcChannel {

    private final JdbcTemplate jdbcTemplate;

    public JdbcChannel(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Channel> channels) {
        final String sql = "INSERT INTO CHANNEL "
                + "(slack_id, name) "
                + "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Channel channel = channels.get(i);

                ps.setString(1, channel.getSlackId());
                ps.setString(2, channel.getName());
            }

            @Override
            public int getBatchSize() {
                return channels.size();
            }
        });
    }
}
