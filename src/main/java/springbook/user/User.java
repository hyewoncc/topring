package springbook.user;

import java.util.Objects;

public class User {

    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    public User() {
    }

    public User(final String id, final String name, final String password, final Level level, final int login,
                final int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public boolean canUpgrade() {
        return level.canUpgrade(this);
    }

    public void upgradeLevel() {
        validateNextLevel();
        this.level = this.level.next();
    }

    private void validateNextLevel() {
        if (this.level.next() == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(final int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(final int recommend) {
        this.recommend = recommend;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }
}
