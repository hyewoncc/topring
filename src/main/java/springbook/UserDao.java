package springbook;

import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                (final ResultSet resultSet, final int rowNum) -> {
                    final var user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                });
    }

    public User get(final String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                (final ResultSet resultSet, final int rowNum) -> {
                    final var user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                });
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
