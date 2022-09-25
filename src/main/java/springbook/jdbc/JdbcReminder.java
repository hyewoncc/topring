package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Reminder;

@Repository
public class JdbcReminder {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReminder(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Reminder> reminders) {
        final String sql = "INSERT INTO REMINDER "
                + "(member_id, message_id, remind_date) "
                + "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Reminder reminder = reminders.get(i);

                ps.setLong(1, reminder.getMemberId());
                ps.setLong(2, reminder.getMessageId());
                ps.setTimestamp(3, Timestamp.valueOf(reminder.getRemindDate()));
            }

            @Override
            public int getBatchSize() {
                return reminders.size();
            }
        });
    }
}
