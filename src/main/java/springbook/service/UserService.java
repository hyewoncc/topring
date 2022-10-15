package springbook.service;

import springbook.dao.UserDao;
import springbook.user.Level;
import springbook.user.User;

public class UserService {

    private UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(final User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    public void upgradeLevels() {
        final var users = userDao.getAll();

        for (User user : users) {
            upgradeLevel(user);
        }
    }

    private void upgradeLevel(final User user) {
        if (user.canUpgrade()) {
            user.upgradeLevel();
            userDao.update(user);
        }
    }
}
