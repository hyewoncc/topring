package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.ChannelSubscription;

@Repository
public class JdbcChannelSubscription {

    private final JdbcTemplate jdbcTemplate;

    public JdbcChannelSubscription(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<ChannelSubscription> subscriptions) {
        final String sql = "INSERT INTO channel_subscription "
                + "(view_order, channel_id, member_id) "
                + "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                ChannelSubscription subscription = subscriptions.get(i);

                ps.setInt(1, subscription.getViewOrder());
                ps.setLong(2, subscription.getChannelId());
                ps.setLong(3, subscription.getMemberId());
            }

            @Override
            public int getBatchSize() {
                return subscriptions.size();
            }
        });
    }
}
