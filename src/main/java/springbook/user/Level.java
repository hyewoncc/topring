package springbook.user;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Level {
    BASIC(1),
    SILVER(2),
    GOLD(3),
    ;

    private final int value;

    Level(final int value) {
        this.value = value;
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
}
