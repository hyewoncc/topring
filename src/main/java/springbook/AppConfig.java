package springbook;

import javax.sql.DataSource;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.dao.UserDaoJdbc;
import springbook.service.UserService;

@SpringBootConfiguration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserService(userDao());
    }

    @Bean
    public UserDaoJdbc userDao() {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/springbook?serverTimezone=Asia/Seoul");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");

        return dataSource;
    }
}
