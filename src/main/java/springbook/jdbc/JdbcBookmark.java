package springbook.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springbook.domain.Bookmark;

@Repository
public class JdbcBookmark {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookmark(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(final List<Bookmark> bookmarks) {
        final String sql = "INSERT INTO BOOKMARK "
                + "(member_id, message_id, created_date) "
                + "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                Bookmark bookmark = bookmarks.get(i);

                ps.setLong(1, bookmark.getMemberId());
                ps.setLong(2, bookmark.getMessageId());
                ps.setObject(3, bookmark.getCreatedDate());
            }

            @Override
            public int getBatchSize() {
                return bookmarks.size();
            }
        });
    }
}
