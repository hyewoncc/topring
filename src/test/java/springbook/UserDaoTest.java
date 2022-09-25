package springbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

class UserDaoTest {

    private UserDao userDao;
    private JdbcContext jdbcContext;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        userDao.setDataSource(dataSource());
        jdbcContext = new JdbcContext(dataSource());
        userDao.setJdbcContext(jdbcContext);
    }

    private DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/springbook?serverTimezone=Asia/Seoul");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");

        return dataSource;
    }

    @DisplayName("사용자 추가 및 조회")
    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user = new User("yellow-cat", "노란 고양이", "password");
        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);

        User foundUser = userDao.get(user.getId());
        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }

    @DisplayName("존재하지 않는 id 조회 시 예외")
    @Test
    void get_idDoesNotExists_throwException() throws SQLException {
        userDao.deleteAll();

        assertThatThrownBy(() -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
