package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Message;

@Repository
public class JdbcMessage {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMessage(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Message> messages) {
        final String sql = "INSERT INTO message "
                + "(modified_date, posted_date, slack_message_id, text, channel_id, member_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Message message = messages.get(i);

                ps.setTimestamp(1, Timestamp.valueOf(message.getModifiedDate()));
                ps.setTimestamp(2, Timestamp.valueOf(message.getPostedDate()));
                ps.setString(3, message.getSlackId());
                ps.setString(4, message.getText());
                ps.setLong(5, message.getChannelId());
                ps.setLong(6, message.getMemberId());
            }

            @Override
            public int getBatchSize() {
                return messages.size();
            }
        });
    }
}
