package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskBiz;
import cn.metaq.sqlbuilder.dto.SqlbuilderModelDTO;
import cn.metaq.sqlbuilder.model.SqlbuilderModel;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型定义api
 *
 * @author zantang
 */
@RestController
@Log4j2
public class SqlbuilderModelController extends BaseController<SqlbuilderModelBiz> {

  @Resource
  private JdbcSqlExecutor executor;
  @Resource
  private SqlbuilderModelTaskBiz taskBiz;

  @PostMapping("models")
  public Result create(@RequestBody SqlbuilderModelDTO model) {

    SqlbuilderModel sqlbuilderModel = new SqlbuilderModel();
    sqlbuilderModel.setName(model.getName());
    sqlbuilderModel.setDefinition(JsonUtils.toJson(model));

    baseBiz.save(sqlbuilderModel);
    return Result.ok();
  }

  /**
   * 运行模型
   * @param modelId
   * @return
   */
  @GetMapping("models/{modelId}")
  public Result execute(@PathVariable Long modelId){

    //查询模型
    SqlbuilderModel sqlbuilderModel = baseBiz.getOneById(SqlbuilderModel.class, modelId);
    //提交一个task并执行
    SqlbuilderModelDTO sqlbuilderModelDTO = JsonUtils
        .fromJson(sqlbuilderModel.getDefinition(), SqlbuilderModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = sqlbuilderModelDTO.getDetails().build(spec).getQuery().validate().toString();

    SqlbuilderModelTask task = new SqlbuilderModelTask();
    task.setBuild(sql);
    task.setImprovement(sql);
    task.setName(sqlbuilderModel.getName());
    task.setType(0);

    return Result.ok(taskBiz.execute(task));
  }

  @PostMapping("test/models")
  public Result test(@RequestBody SqlbuilderModelDTO model) {

    if (model.getDebug()) {
      DbSpec spec = new DbSpec();
      String sql = model.getDetails().build(spec, true).getQuery().validate().toString();

      log.debug("build sql: {}", sql);
      List<Map<String, Object>> data = executor.execute(sql);
      return Result.ok(data);
    }

    return Result.ok();
  }
}
