package springbook;

import springbook.dao.UserDao;
import springbook.service.UserServiceImpl;
import springbook.user.User;

public class ExceptionUserService extends UserServiceImpl {

    private String id;

    public ExceptionUserService(final UserDao userDao, final String id) {
        super(userDao);
        this.id = id;
    }

    @Override
    protected void upgradeLevel(final User user) {
        if (user.getId().equals(this.id)) {
            throw new UserServiceTestException();
        }
        super.upgradeLevel(user);
    }

    static class UserServiceTestException extends RuntimeException {
    }
}
