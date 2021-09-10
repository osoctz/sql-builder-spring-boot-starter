package cn.metaq.sqlbuilder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner run() {
//
//        return args -> {
//            String sql = "select id, name,age from t_person2";
//            List<Person> list = jdbcTemplate.query(sql, new RowMapper<Person>() {
//                @Override
//                public Person mapRow(ResultSet rs, int i) throws SQLException {
//                    Person p = new Person();
//                    p.setId(rs.getString(1));
//                    p.setName(rs.getString(2));
//                    p.setAge(rs.getInt(3));
//                    return p;
//                }
//            });
//
//            int count=0;
//            sql = "insert into t_person2 (name,age) values(?,?)";
//            List<Object[]> batchArgs=new ArrayList<>();
//
//            for (Person person : list) {
//
//                for (int i = 0; i < 500000; i++) {
//
//                    batchArgs.add(new Object[]{person.getName()+i,person.getAge()});
//
//                    if(batchArgs.size()==20000){
//                        jdbcTemplate.batchUpdate(sql,batchArgs);
//                        batchArgs.clear();
//                        count++;
//                        System.out.println("提交批次:"+count);
//                    }
//                }
//            }
//
//            if(batchArgs.size()!=0){
//                jdbcTemplate.batchUpdate(sql,batchArgs);
//                batchArgs.clear();
//            }
//        };
//    }
//
//    @Setter
//    @Getter
//    public class Person {
//
//        private String id;
//        private String name;
//        private int age;
//    }
}
