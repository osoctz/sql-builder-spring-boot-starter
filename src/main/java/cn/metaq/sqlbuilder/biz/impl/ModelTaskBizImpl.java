package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.data.jpa.BaseTemplate;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskExtDao;
import cn.metaq.sqlbuilder.dao.TaskDao;
import cn.metaq.sqlbuilder.dto.ModelTaskDTO;
import cn.metaq.sqlbuilder.model.ModelTaskExt;
import cn.metaq.sqlbuilder.model.Task;
import cn.metaq.sqlbuilder.qo.ModelTaskQo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型任务业务逻辑接口
 *
 * @author zantang
 */
@Service
public class ModelTaskBizImpl extends BaseBiz<ModelTaskExt, Long, ModelTaskExtDao> implements ModelTaskBiz {

  /** task */
  @Resource
  private TaskDao taskDao;

  @Resource
  private BaseTemplate baseTemplate;

  @Override
  public Pagination<ModelTaskDTO> list(ModelTaskQo modelTaskQo, int offset, int limit) {

    String jql =
        "select new ai.bailian.sqlbuilder.model.dto.ModelTaskDTO(a.id," + "a.name," + "a.type," + "a.mode," + "b.build,"
            + "b.improvement," + "b.columns," + "b.mid," + "a.createdBy," + "a.createdTs," + "a.updatedBy,"
            + "a.updatedTs)" + " from Task a left join ModelTaskExt b on a.id=b.id where a.type=0";

    Map<String, Object> params = new HashMap<>(3);

    jql = buildQueryString(modelTaskQo, jql, params);

    List<ModelTaskDTO> list = baseTemplate.list(ModelTaskDTO.class, jql, params, offset, limit);

    Pagination pagination = new Pagination();
    pagination.setOffset(offset);
    pagination.setLimit(limit);
    pagination.setTotal(Math.toIntExact(this.count(modelTaskQo)));
    pagination.setData(list);
    return pagination;
  }

  /**
   * 构建查询
   *
   * @param queryDTO
   * @param jql
   * @param params
   * @return
   */
  private String buildQueryString(ModelTaskQo queryDTO, String jql, Map<String, Object> params) {
    if (queryDTO.getMid() != null) {
      jql += " and b.mid=:mid";
      params.put("mid", queryDTO.getMid());
    }
    if (queryDTO.getMode() != null) {
      jql += " and a.mode=:mode";
      params.put("mode", queryDTO.getMode());
    }
    if (queryDTO.getName() != null && queryDTO.getName().length() > 0) {
      jql += " and a.name like :name";
      params.put("name", "%" + queryDTO.getName() + "%");
    }
    return jql;
  }

  @Override
  public void save(ModelTaskDTO modelTaskDTO) {

    Task task = new Task();
    task.setName(modelTaskDTO.getName());
    task.setType(modelTaskDTO.getType());
    task.setMode(modelTaskDTO.getMode());

    taskDao.save(task);

    ModelTaskExt modelTaskExt = new ModelTaskExt();
    modelTaskExt.setId(task.getId());
    modelTaskExt.setMid(modelTaskDTO.getMid());
    modelTaskExt.setBuild(modelTaskDTO.getBuild());
    modelTaskExt.setImprove(modelTaskDTO.getImprove());
    modelTaskExt.setColumns(modelTaskDTO.getColumns());

    dao.save(modelTaskExt);

    modelTaskDTO.setId(task.getId());
  }

  @Override
  public <R> R getOneById(Class<R> resultClass, Long id) {

    ModelTaskExt modelTaskExt = dao.findById(id).get();
    Task task = taskDao.findById(id).get();

    ModelTaskDTO modelTaskDTO = new ModelTaskDTO();

    modelTaskDTO.setId(task.getId());
    modelTaskDTO.setName(task.getName());
    modelTaskDTO.setType(task.getType());
    modelTaskDTO.setMode(task.getMode());
    modelTaskDTO.setCreatedBy(task.getCreatedBy());
    modelTaskDTO.setCreatedTs(task.getCreatedTs());
    modelTaskDTO.setUpdatedBy(task.getUpdatedBy());
    modelTaskDTO.setUpdateTs(task.getUpdatedTs());
    modelTaskDTO.setBuild(modelTaskExt.getBuild());
    modelTaskDTO.setImprove(modelTaskExt.getImprove());
    modelTaskDTO.setColumns(modelTaskExt.getColumns());
    modelTaskDTO.setMid(modelTaskExt.getMid());

    return (R) modelTaskDTO;
  }

  public Long count(ModelTaskQo queryDTO) {

    String jql = "select count(1) from Task a left join ModelTaskExt b on a.id=b.id where a.type=0";

    Map<String, Object> params = new HashMap<>(3);
    jql = buildQueryString(queryDTO, jql, params);

    return baseTemplate.count(jql, params);
  }

  @Override
  public Specification map(ModelTaskExt modelTask) {

    ModelTaskQo modelTaskDTO = new ModelTaskQo();
    modelTaskDTO.setMid(modelTask.getMid());
    return this.map(modelTaskDTO);
  }

  public Specification map(ModelTaskQo queryDTO) {

    return (Specification<ModelTaskExt>) (root, cq, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (null != queryDTO.getMid()) {
        predicates.add(cb.equal(root.get("mid"), queryDTO.getMid()));
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }
}
