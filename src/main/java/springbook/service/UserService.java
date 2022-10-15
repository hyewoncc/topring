package springbook.service;

import java.sql.SQLException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.dao.UserDao;
import springbook.user.Level;
import springbook.user.User;

public class UserService {

    private UserDao userDao;
    private PlatformTransactionManager transactionManager;

    public UserService(final UserDao userDao, final PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    public void add(final User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    public void upgradeLevels() throws SQLException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            final var users = userDao.getAll();

            for (User user : users) {
                upgradeLevel(user);
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    protected void upgradeLevel(final User user) {
        if (user.canUpgrade()) {
            user.upgradeLevel();
            userDao.update(user);
        }
    }
}
