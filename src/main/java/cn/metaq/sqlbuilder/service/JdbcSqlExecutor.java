package cn.metaq.sqlbuilder.service;

import cn.metaq.std.sqlbuilder.service.SqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JdbcSqlExecutor implements SqlExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> execute(String sql) {

        List<Map<String, Object>> rows=jdbcTemplate.query(sql, new ColumnMapRowMapper());
        return rows;
    }
}
