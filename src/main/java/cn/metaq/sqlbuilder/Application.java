package cn.metaq.sqlbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author zantang
 */
@EnableOpenApi
@SpringBootApplication
@EnableMongoRepositories(excludeFilters = {
    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = cn.metaq.data.jpa.BaseDao.class)})
@EnableJdbcRepositories(excludeFilters = {
    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = cn.metaq.data.jpa.BaseDao.class)})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
