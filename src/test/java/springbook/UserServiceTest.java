package springbook;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.ExceptionUserService.UserServiceTestException;
import springbook.dao.UserDao;
import springbook.service.UserService;
import springbook.user.Level;
import springbook.user.User;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

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
    void upgradeLevels() throws SQLException {
        final var basicShouldNotBeUpgraded = new User("cat", "고양이", "password", Level.BASIC, Level.SILVER.login - 1, 0);
        final var basicShouldBeSilver = new User("dog", "개", "password", Level.BASIC, Level.SILVER.login, 0);
        final var silverShouldNotBeUpgraded = new User("bird", "새", "password", Level.SILVER, 60,
                Level.GOLD.recommend - 1);
        final var silverShouldBeGold = new User("mouse", "쥐", "password", Level.SILVER, 60, Level.GOLD.recommend);
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

        assertLevelUpgraded(basicShouldNotBeUpgraded, false);
        assertLevelUpgraded(basicShouldBeSilver, true);
        assertLevelUpgraded(silverShouldNotBeUpgraded, false);
        assertLevelUpgraded(silverShouldBeGold, true);
        assertLevelUpgraded(basicShouldNotBeUpgraded, false);
    }

    private void assertLevelUpgraded(final User user, final boolean upgraded) {
        final var result = userDao.get(user.getId());
        if (upgraded) {
            assertThat(result.getLevel()).isEqualTo(user.getLevel().next());
            return;
        }
        assertThat(result.getLevel()).isEqualTo(user.getLevel());
    }

    @DisplayName("예외 발생 시 모두 롤백")
    @Test
    void updateAll_throwsException_rollbackAll() {
        final var basicShouldNotBeUpgraded = new User("a_cat", "고양이", "password", Level.BASIC, Level.SILVER.login - 1, 0);
        final var basicShouldBeSilver = new User("b_dog", "개", "password", Level.BASIC, Level.SILVER.login, 0);
        final var silverShouldNotBeUpgraded = new User("c_bird", "새", "password", Level.SILVER, 60,
                Level.GOLD.recommend - 1);
        final var silverShouldBeGold = new User("d_mouse", "쥐", "password", Level.SILVER, 60, Level.GOLD.recommend);
        final var goldShouldNotBeChanged = new User("e_fish", "물고기", "password", Level.GOLD, 100, 100);

        final var users = List.of(
                basicShouldNotBeUpgraded,
                basicShouldBeSilver,
                silverShouldNotBeUpgraded,
                silverShouldBeGold,
                goldShouldNotBeChanged
        );

        UserService testUserService = new ExceptionUserService(userDao, transactionManager, silverShouldNotBeUpgraded.getId());

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
        } catch (UserServiceTestException | SQLException ignored) {
        }

        assertLevelUpgraded(basicShouldBeSilver, false);
    }
}
