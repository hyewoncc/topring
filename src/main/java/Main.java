import java.sql.SQLException;
import springbook.User;
import springbook.UserDao;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao();

        User user = new User("hyewoncc2", "최혜원", "password");
        userDao.add(user);

        System.out.println("등록 성공");

        User foundUser = userDao.get(user.getId());
        System.out.println(foundUser.getName());
        System.out.println(foundUser.getPassword());
        System.out.println(foundUser.getId() + " 조회 성공");
    }
}
