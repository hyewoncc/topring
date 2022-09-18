package springbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

class UserDaoTest {

    @DisplayName("사용자 추가 및 조회")
    @Test
    void addAndGet() throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

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
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        assertThatThrownBy(() -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
