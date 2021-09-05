package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.DateUtils;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import cn.metaq.sqlbuilder.service.SqlbuilderRecordService;
import cn.metaq.std.sqlbuilder.step.SqlbuilderStep;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "model")
@Log4j2
public class SqlbuilderEndpoint {

    @Resource
    private JdbcSqlExecutor executor;

    @Resource
    private SqlbuilderRecordService sqlbuilderRecordService;

    @PostMapping("preview")
    public Result preview(@RequestBody SqlVisualModel model) {

        SqlbuilderStep sbs = model.getData();

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        String sql = sbs.build(spec, schema).getQuery().validate().toString();

        List<Map<String, Object>> data = executor.execute(sql);
        return Result.ok(data);
    }

    @PostMapping("exec")
    public Result execute(@RequestBody SqlVisualModel model) {

        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        String sql = model.getData().build(spec, schema).getQuery().validate().toString();

        long start = System.currentTimeMillis();
        List<Map<String, Object>> data = executor.execute(sql);
        long end = System.currentTimeMillis();

        System.out.println("查询花费时间 :" + (end-start)/1000 +"s");
        SqlbuilderRecord record = new SqlbuilderRecord();
        record.setViewSql(sql);
        record.setCreateTs(DateUtils.getCurrent());

        sqlbuilderRecordService.save(record, data);
        return Result.ok(record);
    }
}
