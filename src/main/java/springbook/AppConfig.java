package springbook;

import javax.sql.DataSource;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.dao.UserDaoJdbc;
import springbook.service.UserService;
import springbook.service.UserServiceImpl;
import springbook.service.UserServiceTx;

@SpringBootConfiguration
public class AppConfig {

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDao());
    }

    @Bean
    public UserService userService() {
        return new UserServiceTx(userServiceImpl(), transactionManager());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
