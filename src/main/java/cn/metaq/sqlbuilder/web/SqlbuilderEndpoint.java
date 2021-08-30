package cn.metaq.sqlbuilder.web;

import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.SqlExecutor;
import cn.metaq.sqlbuilder.SqlbuilderStep;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Log4j2
public class SqlbuilderEndpoint {


    @PostMapping("sqlbuilder")
    public Result build(@RequestBody SqlVisualModel model) {

        SqlbuilderStep sbs = model.getData();

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        String sql = sbs.build(spec, schema).getQuery().validate().toString();

        return Result.ok(((SqlExecutor) s -> {
            log.info("sql ={} " , s);
            return new ArrayList<>();
        }).execute(sql));
    }
}
