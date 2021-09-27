package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelBiz;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.biz.ModelTemplateBiz;
import cn.metaq.sqlbuilder.dto.ModelDTO;
import cn.metaq.sqlbuilder.model.Model;
import cn.metaq.sqlbuilder.model.ModelTask;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import cn.metaq.sqlbuilder.service.JdbcSqlExecutor;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import io.swagger.annotations.ApiOperation;
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
public class ModelController extends BaseController<ModelBiz> {

  @Resource
  private JdbcSqlExecutor executor;
  @Resource
  private ModelTaskBiz taskBiz;
  @Resource
  private ModelTemplateBiz modelTemplateBiz;

  @PostMapping("models")
  public Result create(@RequestBody ModelDTO model) {

    Model sqlbuilderModel = new Model();
    sqlbuilderModel.setName(model.getName());
    sqlbuilderModel.setDefinition(JsonUtils.toJson(model));

    baseBiz.save(sqlbuilderModel);
    return Result.ok();
  }

  /**
   * 运行模型
   *
   * @param modelId
   * @return
   */
  @GetMapping("models/{modelId}")
  public Result execute(@PathVariable Long modelId) {

    //查询模型
    Model model = baseBiz.getOneById(Model.class, modelId);
    //提交一个task并执行
    ModelDTO modelDTO = JsonUtils
        .fromJson(model.getDefinition(), ModelDTO.class);
    DbSpec spec = new DbSpec();
    String sql = modelDTO.getDetails().build(spec).getQuery().validate().toString();

    ModelTask task = new ModelTask();
    task.setBuild(sql);
    task.setImprovement(sql);
    task.setName(model.getName());
    task.setType(0);

    return Result.ok(taskBiz.execute(task));
  }

  @PostMapping("test/models")
  public Result test(@RequestBody ModelDTO model) {

    if (model.getDebug()) {
      DbSpec spec = new DbSpec();
      String sql = model.getDetails().build(spec, true).getQuery().validate().toString();

      log.debug("build sql: {}", sql);
      List<Map<String, Object>> data = executor.execute(sql);
      return Result.ok(data);
    }

    return Result.ok();
  }

  @ApiOperation("设置模型为模版")
  @GetMapping("{modelId}/templates")
  public Result useTemplate(@PathVariable Long modelId) {
    //查询模型
    Model model = baseBiz.getOneById(Model.class, modelId);

    ModelTemplate template = new ModelTemplate();
    template.setName(model.getName());
    template.setDefinition(model.getDefinition());
    template.setMid(model.getId());

    modelTemplateBiz.save(template);
    return Result.ok(template);
  }
}
