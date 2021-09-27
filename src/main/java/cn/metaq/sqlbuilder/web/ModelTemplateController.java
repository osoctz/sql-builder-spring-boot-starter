package cn.metaq.sqlbuilder.web;

import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelTemplateBiz;
import cn.metaq.sqlbuilder.dto.ModelDTO;
import cn.metaq.sqlbuilder.model.Model;
import cn.metaq.sqlbuilder.model.ModelTemplate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型模版api
 *
 * @author zantang
 */
@RestController
@Log4j2
public class ModelTemplateController extends BaseController<ModelTemplateBiz> {

  @ApiOperation("根据模版id获取模型的json")
  @GetMapping("modelTemplate/{id}")
  public Result getTemplate(@PathVariable Long id) {

    ModelTemplate model = baseBiz.getOneById(ModelTemplate.class, id);

    String json = model.getDefinition();
    ModelDTO modelDTO = JsonUtils.fromJson(json, ModelDTO.class);
    return Result.ok(modelDTO.getDetails());
  }
}
