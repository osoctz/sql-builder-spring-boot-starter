package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskBiz;
import cn.metaq.sqlbuilder.biz.SqlbuilderModelTaskRecordBiz;
import cn.metaq.sqlbuilder.dto.SqlbuilderModelDTO;
import cn.metaq.sqlbuilder.dto.SqlbuilderSubmitTaskDTO;
import cn.metaq.sqlbuilder.model.SqlbuilderModel;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTask;
import cn.metaq.sqlbuilder.model.SqlbuilderModelTaskRecord;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型运行任务api
 *
 * @author zantang
 */
@RestController
@Log4j2
public class SqlbuilderModelTaskController extends BaseController<SqlbuilderModelTaskBiz> {

  @Resource
  private SqlbuilderModelBiz sqlbuilderModelBiz;

  @Resource
  private SqlbuilderModelTaskRecordBiz sqlbuilderModelTaskRecordBiz;

  @PostMapping("{modelId}/tasks")
  public Result submit(@RequestBody SqlbuilderSubmitTaskDTO taskDTO, @PathVariable Long modelId) {

    SqlbuilderModel sqlbuilderModel = sqlbuilderModelBiz
        .getOneById(SqlbuilderModel.class, modelId);

    SqlbuilderModelDTO sqlbuilderModelDTO = JsonUtils
        .fromJson(sqlbuilderModel.getDefinition(), SqlbuilderModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = sqlbuilderModelDTO.getDetails().build(spec).getQuery().validate().toString();

    SqlbuilderModelTask task = new SqlbuilderModelTask();
    task.setBuild(sql);
    task.setImprovement(
        StringUtils.isEmpty(taskDTO.getImprovement()) ? sql : taskDTO.getImprovement());
    task.setName(sqlbuilderModel.getName());
    task.setType(taskDTO.getTaskType());

    baseBiz.save(task);
    return Result.ok();
  }

  @GetMapping("tasks")
  public Result execute(@RequestParam Long taskId) {

    SqlbuilderModelTask task = baseBiz.getOneById(SqlbuilderModelTask.class, taskId);

    SqlbuilderModelTaskRecord record = sqlbuilderModelTaskRecordBiz.execute(task);

    return Result.ok(record);
  }
}
