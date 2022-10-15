package springbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springbook.user.Level;
import springbook.user.User;

class UserTest {

    @DisplayName("정상적인 레벨 업그레이드")
    @Test
    void updateLevel() {
        final var user = new User("cat", "고양이", "password", Level.BASIC, 0, 0);
        final var levels = Level.values();
        for (Level level : levels) {
            if (level.next() != null) {
                user.setLevel(level);
                user.upgradeLevel();
                assertThat(user.getLevel()).isEqualTo(level.next());
            }
        }
    }

    @DisplayName("업그레이드할 레벨이 없을 때 예외 발생")
    @Test
    void updateLevel_throwsException() {
        final var user = new User("cat", "고양이", "password", Level.BASIC, 0, 0);
        final var levels = Level.values();
        for (Level level : levels) {
            if (level.next() == null) {
                user.setLevel(level);
                assertThatThrownBy(() -> user.upgradeLevel())
                        .isInstanceOf(IllegalStateException.class);
            }
        }
    }
}
