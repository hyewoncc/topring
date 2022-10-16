package springbook.dao;

import java.util.ArrayList;
import java.util.List;
import springbook.user.User;

public class MockUserDao implements UserDao {

    private List<User> users;
    private List<User> updated = new ArrayList<>();

    public MockUserDao(final List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void update(final User user) {
        updated.add(user);
    }

    public List<User> getUpdated() {
        return updated;
    }

    @Override
    public void add(final User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User get(final String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException();
    }
}
