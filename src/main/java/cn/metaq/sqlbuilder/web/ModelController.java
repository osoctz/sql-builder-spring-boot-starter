package cn.metaq.sqlbuilder.web;

import static cn.metaq.sqlbuilder.constants.TaskMode.IMMEDIATE;
import static cn.metaq.sqlbuilder.constants.TaskType.MODEL;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.biz.ModelTemplateBiz;
import cn.metaq.sqlbuilder.model.dto.ModelDTO;
import cn.metaq.sqlbuilder.model.dto.ModelTaskDTO;
import cn.metaq.sqlbuilder.model.entity.Model;
import cn.metaq.sqlbuilder.model.entity.ModelTemplate;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 模型定义api
 *
 * @author zantang
 */
@RestController
@Log4j2
public class ModelController extends BaseController<ModelBiz> {

  @Resource
  private JdbcSqlExecutor executor;
  @Resource
  private ModelTaskBiz taskBiz;
  @Resource
  private ModelTemplateBiz modelTemplateBiz;
  @Resource
  private ApplicationContext ac;

  /**
   * 创建模型
   *
   * @param model
   * @return
   */
  @ApiOperation("保存模型")
  @PostMapping("models")
  public Result create(@RequestBody ModelDTO model) {

    Model sqlbuilderModel = new Model();
    sqlbuilderModel.setName(model.getName());
    sqlbuilderModel.setDefinition(JsonUtils.toJson(model));

    baseBiz.save(sqlbuilderModel);
    return Result.ok(sqlbuilderModel);
  }

  /**
   * 运行模型
   *
   * @param modelId
   * @return
   */
  @ApiOperation("运行模型")
  @GetMapping("models/executions")
  public Result execute(@RequestParam Long modelId) {
    // 查询模型
    Model model = baseBiz.getOneById(Model.class, modelId);
    // 提交一个task并执行
    ModelDTO modelDTO = JsonUtils.fromJson(model.getDefinition(), ModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = modelDTO.getDetails().build(spec).getQuery().validate().toString();
    log.debug("execute sql: {}", sql);

    ModelTaskDTO task = new ModelTaskDTO();
    task.setMid(model.getId());
    task.setBuild(sql);
    task.setImprove(sql);
    task.setName(model.getName());
    task.setType(MODEL.getValue());
    task.setMode(IMMEDIATE.getValue());
    task.setColumns(JsonUtils.toJson(modelDTO.getColumns()));

    taskBiz.save(task);
//    Map<String, Object> source = new HashMap<>(5);
//    source.put("taskId", task.getId());
//
//    ac.publishEvent(new ModelTaskEvent(source));
    return Result.ok(task);
  }

  @ApiOperation("调试")
  @PostMapping("test/models")
  public Result test(@RequestBody ModelDTO model) {

    if (model.getDebug()) {
      DbSpec spec = new DbSpec();
      String sql = model.getDetails().build(spec, true, model.getOffset(), model.getLimit()).getQuery().validate()
          .toString();

      log.debug("build sql: {}", sql);
      List<Map<String, Object>> data = executor.execute(sql);
      return Result.ok(data);
    }

    return Result.ok();
  }

  @ApiOperation("预览")
  @PostMapping("show/models")
  public Result show(@RequestBody ModelDTO model) {

    DbSpec spec = new DbSpec();
    String sql = model.getDetails().build(spec, model.getDebug()).getQuery().validate().toString();

    log.debug("build sql: {}", sql);
    List<Map<String, Object>> data = executor.execute(sql);

    Map<String, Object> result = new HashMap<>();
    result.put("sql", sql);
    result.put("data", data);
    return Result.ok(result);
  }

  @ApiOperation("设置模型为模版")
  @GetMapping("{modelId}/templates")
  public Result useTemplate(@PathVariable Long modelId) {
    // 查询模型
    Model model = baseBiz.getOneById(Model.class, modelId);

    ModelTemplate template = new ModelTemplate();
    template.setName(model.getName());
    template.setDefinition(model.getDefinition());
    template.setMid(model.getId());

    modelTemplateBiz.save(template);
    return Result.ok(template);
  }

  @ApiOperation("获取模版")
  @GetMapping("models")
  public Result getModel(@RequestParam Long modelId) {

    return Result.ok(baseBiz.getOneById(Model.class, modelId));
  }

  @ApiOperation("编辑模版")
  @PostMapping("models/{modelId}")
  public Result editModel(@RequestBody ModelDTO model, @PathVariable Long modelId) {

    Model _model = baseBiz.getOneById(Model.class, modelId);
    _model.setId(modelId);
    _model.setName(model.getName());
    _model.setDefinition(JsonUtils.toJson(model));
    baseBiz.update(_model);
    return Result.ok(_model);
  }

  @ApiOperation("删除模版")
  @GetMapping("models/{modelId}")
  public Result deleteModel(@PathVariable Long modelId) {

    baseBiz.deleteById(Model.class, modelId);
    return Result.ok();
  }
}
