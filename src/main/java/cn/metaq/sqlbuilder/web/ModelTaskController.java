package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.biz.TaskRecordBiz;
import cn.metaq.sqlbuilder.dto.ModelDTO;
import cn.metaq.sqlbuilder.model.Model;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTaskRecord;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型运行任务api
 *
 * @author zantang
 */
@RestController
@Log4j2
public class ModelTaskController extends BaseController<ModelTaskBiz> {

  @Resource
  private ModelBiz modelBiz;

  @Resource
  private TaskRecordBiz taskRecordBiz;

  /**
   * 提交任务
   *
   * @param taskType
   * @param modelId
   * @return
   */
  @GetMapping("{modelId}/modelTasks")
  public Result submit(@RequestParam Integer taskType, @PathVariable Long modelId) {

    Model model = modelBiz
        .getOneById(Model.class, modelId);

    ModelDTO modelDTO = JsonUtils
        .fromJson(model.getDefinition(), ModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = modelDTO.getDetails().build(spec).getQuery().validate().toString();

    ModelTask task = new ModelTask();
    task.setBuild(sql);
    task.setImprovement(sql);
    task.setName(model.getName());
    task.setType(taskType);

    baseBiz.save(task);
    return Result.ok();
  }

  /**
   * 执行任务
   *
   * @param taskId
   * @return
   */
  @GetMapping("modelTasks/{taskId}")
  public Result execute(@PathVariable Long taskId) {

    ModelTask task = baseBiz.getOneById(ModelTask.class, taskId);

    ModelTaskRecord record = taskRecordBiz.execute(task);

    return Result.ok(record);
  }

}
