package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.DateUtils;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.model.SqlbuilderRecord;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import cn.metaq.sqlbuilder.service.SqlbuilderRecordService;
import cn.metaq.std.sqlbuilder.step.SqlbuilderStep;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可视化建模接口
 *
 * @author zantang
 */
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

    SqlbuilderStep sbs = model.getDetails();

    DbSpec spec = new DbSpec();
    String sql = sbs.build(spec, model.isDebug()).getQuery().validate().toString();
    log.debug("sql build:{}", sql);
    List<Map<String, Object>> data = executor.execute(sql);
    return Result.ok(data);
  }

  @PostMapping("exec")
  public Result execute(@RequestBody SqlVisualModel model) {

    DbSpec spec = new DbSpec();
    String sql = model.getDetails().build(spec).getQuery().validate().toString();

    List<Map<String, Object>> data = executor.execute(sql);

    SqlbuilderRecord record = new SqlbuilderRecord();
    record.setViewSql(sql);
    record.setCreateTs(DateUtils.getCurrent());

    sqlbuilderRecordService.save(record, data);
    return Result.ok(record);
  }
}