package springbook;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbook.dao.UserDao;
import springbook.service.UserService;
import springbook.user.Level;
import springbook.user.User;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @DisplayName("Autowired로 userService가 정상 주입된다")
    @Test
    void autowired() {
        assertThat(userService).isNotNull();
    }

    @DisplayName("사용자 추가 시 레벨이 비어있다면 BASIC으로 설정")
    @Test
    void add() {
        userDao.deleteAll();

        final var goldLevelUser = new User("fish", "물고기", "password", Level.GOLD, 100, 100);

        final var nullLevelUser = new User("cat", "고양이", "password", Level.BASIC, 49, 0);
        nullLevelUser.setLevel(null);

        userService.add(goldLevelUser);
        userService.add(nullLevelUser);

        final var goldLevelUserResult = userDao.get(goldLevelUser.getId());
        final var nullLevelUserResult = userDao.get(nullLevelUser.getId());

        assertThat(goldLevelUserResult.getLevel()).isEqualTo(Level.GOLD);
        assertThat(nullLevelUserResult.getLevel()).isEqualTo(Level.BASIC);
    }

    @DisplayName("조건에 따른 레벨 변경")
    @Test
    void upgradeLevels() {
        final var basicShouldNotBeUpgraded = new User("cat", "고양이", "password", Level.BASIC, 49, 0);
        final var basicShouldBeSilver = new User("dog", "개", "password", Level.BASIC, 50, 0);
        final var silverShouldNotBeUpgraded = new User("bird", "새", "password", Level.SILVER, 60, 29);
        final var silverShouldBeGold = new User("mouse", "쥐", "password", Level.SILVER, 60, 30);
        final var goldShouldNotBeChanged = new User("fish", "물고기", "password", Level.GOLD, 100, 100);

        final var users = List.of(
                basicShouldNotBeUpgraded,
                basicShouldBeSilver,
                silverShouldNotBeUpgraded,
                silverShouldBeGold,
                goldShouldNotBeChanged
        );

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        assertLevel(basicShouldNotBeUpgraded, Level.BASIC);
        assertLevel(basicShouldBeSilver, Level.SILVER);
        assertLevel(silverShouldNotBeUpgraded, Level.SILVER);
        assertLevel(silverShouldBeGold, Level.GOLD);
        assertLevel(goldShouldNotBeChanged, Level.GOLD);
    }

    private void assertLevel(final User user, final Level level) {
        final var result = userDao.get(user.getId());
        assertThat(result.getLevel()).isEqualTo(level);
    }
}
