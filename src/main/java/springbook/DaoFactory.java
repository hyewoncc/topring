package springbook;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(makeDBConnector());
    }

    private DBConnector makeDBConnector() {
        return new GoogleDBConnector();
    }
}
