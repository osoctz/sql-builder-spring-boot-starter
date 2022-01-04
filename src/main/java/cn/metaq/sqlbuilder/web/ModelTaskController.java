package cn.metaq.sqlbuilder.web;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.model.dto.ModelDTO;
import cn.metaq.sqlbuilder.model.dto.ModelTaskDTO;
import cn.metaq.sqlbuilder.model.entity.Model;
import cn.metaq.sqlbuilder.model.qo.ModelTaskQo;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
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
public class ModelTaskController extends BaseController<ModelTaskBiz> {

  @Resource
  private ModelBiz modelBiz;
  @Resource
  private ApplicationContext ac;

  @ApiOperation("模型任务分页接口")
  @PostMapping("modelTask/pages")
  public Pagination<ModelTaskDTO> list(@RequestBody ModelTaskQo modelTaskQo, int offset, int limit) {

    return baseBiz.list(modelTaskQo, offset, limit);
  }

  /**
   * 提交任务
   *
   * @param taskType
   * @param modelId
   * @return
   */
  @ApiOperation("提交模型任务[*]")
  @GetMapping("{modelId}/modelTasks")
  public Result submit(@RequestParam Integer taskType, @PathVariable Long modelId) {

    Model model = modelBiz.getOneById(Model.class, modelId);

    ModelDTO modelDTO = JsonUtils.fromJson(model.getDefinition(), ModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = modelDTO.getDetails().build(spec).getQuery().validate().toString();

    ModelTaskDTO task = new ModelTaskDTO();
    task.setBuild(sql);
    task.setImprove(sql);
    task.setName(model.getName());
    task.setType(taskType);

    baseBiz.save(task);
    return Result.ok(task);
  }

  /**
   * 执行任务
   *
   * @param taskId
   * @return
   */
  @ApiOperation("执行模型任务[*]")
  @GetMapping("modelTasks/{taskId}")
  public Result execute(@PathVariable Long taskId) {

    ModelTaskDTO task = baseBiz.getOneById(ModelTaskDTO.class, taskId);

//    Map<String, Object> source = new HashMap<>(5);
//    source.put("taskId", task.getId());
//
//    ac.publishEvent(new ModelTaskEvent(source));
    return Result.ok(task);
  }
}
