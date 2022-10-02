package springbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserDaoJdbc userDao;

    @DisplayName("사용자 추가 및 조회")
    @Test
    void addAndGet() {
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
    void get_idDoesNotExists_throwException() {
        userDao.deleteAll();

        assertThatThrownBy(() -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("복수 사용자 등록 수 전체 조회")
    @Test
    void findAll() {
        userDao.deleteAll();

        final var cat = new User("cat", "고양이", "password");
        userDao.add(cat);
        final var dog = new User("dog", "개", "password");
        userDao.add(dog);
        final var mouse = new User("mouse", "쥐", "password");
        userDao.add(mouse);

        final var result = userDao.getAll();
        assertThat(result).isEqualTo(List.of(cat, dog, mouse));
    }

    @DisplayName("사용자가 없을 때 전체 조회 시 빈 리스트 반환")
    @Test
    void findAll_noUsers_returnEmptyList() {
        userDao.deleteAll();

        final var result = userDao.getAll();
        assertThat(result).isEmpty();
    }

    @DisplayName("중복된 id로 저장하면 예외 발생")
    @Test
    void add_duplicatedId_throwsException() {
        userDao.deleteAll();

        userDao.add(new User("black_dog", "검은개", "password"));

        assertThatThrownBy(
                () -> userDao.add(new User("black_dog", "검은개", "password"))
        ).isInstanceOf(DuplicateKeyException.class);
    }
}
