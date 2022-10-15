package springbook;

import org.springframework.transaction.PlatformTransactionManager;
import springbook.dao.UserDao;
import springbook.service.UserService;
import springbook.user.User;

public class ExceptionUserService extends UserService {

    private String id;

    public ExceptionUserService(final UserDao userDao, final PlatformTransactionManager transactionManager, final String id) {
        super(userDao, transactionManager);
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
