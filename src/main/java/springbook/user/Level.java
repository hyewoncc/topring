package springbook.user;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public enum Level {
    GOLD(3, null, 0, 30, (login, recommend) -> false),
    SILVER(2, GOLD, 50, 0, (login, recommend) -> recommend >= 30),
    BASIC(1, SILVER, 0, 0, (login, recommend) -> login >= 50),
    ;

    private final int value;
    public final int login;
    public final int recommend;
    private final Level next;
    private final BiFunction<Integer, Integer, Boolean> calculate;

    Level(final int value,
          final Level next,
          final int login,
          final int recommend,
          final BiFunction<Integer, Integer, Boolean> calculate) {
        this.value = value;
        this.next = next;
        this.login = login;
        this.recommend = recommend;
        this.calculate = calculate;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.intValue() == value)
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public int intValue() {
        return value;
    }

    public Level next() {
        return next;
    }

    public boolean canUpgrade(final User user) {
        return calculate.apply(user.getLogin(), user.getRecommend());
    }
}
