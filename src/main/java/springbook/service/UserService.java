package springbook.service;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springbook.dao.UserDao;
import springbook.user.Level;
import springbook.user.User;

public class UserService {

    private UserDao userDao;
    private DataSource dataSource;

    public UserService(final UserDao userDao, final DataSource dataSource) {
        this.userDao = userDao;
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    public void upgradeLevels() throws SQLException {
        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false);

        try {
            final var users = userDao.getAll();

            for (User user : users) {
                upgradeLevel(user);
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

    }

    protected void upgradeLevel(final User user) {
        if (user.canUpgrade()) {
            user.upgradeLevel();
            userDao.update(user);
        }
    }
}
