package springbook.dao;

import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.User;

public class UserDaoJdbc implements UserDao{

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper =
            (final ResultSet resultSet, final int rowNum) -> {
                final var user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            };

    public UserDaoJdbc(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(final User user) {
        jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userRowMapper);
    }

    @Override
    public User get(final String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id}, userRowMapper);
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
