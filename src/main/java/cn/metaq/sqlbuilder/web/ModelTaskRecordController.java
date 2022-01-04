package cn.metaq.sqlbuilder.web;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.common.util.JsonUtils;
import cn.metaq.common.web.BaseController;
import cn.metaq.common.web.dto.Result;
import cn.metaq.sqlbuilder.biz.ModelTaskRecordBiz;
import cn.metaq.sqlbuilder.common.biz.CommonMongoQueryBiz;
import cn.metaq.sqlbuilder.model.dto.ColumnDTO;
import cn.metaq.sqlbuilder.model.entity.ModelTaskRecordExt;
import cn.metaq.sqlbuilder.model.qo.ModelTaskRecordQo;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务执行记录api
 *
 * @author zantang
 */
@RestController
public class ModelTaskRecordController extends BaseController<ModelTaskRecordBiz> {

  @Resource
  private CommonMongoQueryBiz commonMongoQueryBiz;

  @ApiOperation("模型执行记录分页查询")
  @PostMapping("modelTaskRecord/pages")
  public Result list(@RequestBody ModelTaskRecordQo qo, int offset, int limit) {

    return Result.ok(baseBiz.list(qo, offset, limit));
  }

  @ApiOperation("模型执行结果分页查询")
  @PostMapping("{modelTaskRecordId}/data/pages")
  public Result list(int offset, int limit, @PathVariable Long modelTaskRecordId) {

    ModelTaskRecordExt record = baseBiz.getOneById(ModelTaskRecordExt.class, modelTaskRecordId);

    Pagination<HashMap> pagination = commonMongoQueryBiz.list(HashMap.class, null, offset, limit,
        record.getCollection());

    Map<String, Object> result = new HashMap<>(5);
    result.put("columns", JsonUtils.listFromJson(record.getColumns(), ColumnDTO.class));
    result.put("page", pagination);

    return Result.ok(result);
  }
}
