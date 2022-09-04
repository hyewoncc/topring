package springbook;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao(new GoogleDBConnector());

        User user = new User("hyewoncc3", "최혜원", "password");
        userDao.add(user);

        System.out.println("등록 성공");

        User foundUser = userDao.get(user.getId());
        System.out.println(foundUser.getName());
        System.out.println(foundUser.getPassword());
        System.out.println(foundUser.getId() + " 조회 성공");
    }
}
