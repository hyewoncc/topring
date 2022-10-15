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
            var changed = false;
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            }

            if (changed) {
                userDao.update(user);
            }
        }
    }
}
