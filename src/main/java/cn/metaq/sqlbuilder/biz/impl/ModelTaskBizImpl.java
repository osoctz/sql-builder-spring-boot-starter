package cn.metaq.sqlbuilder.biz.impl;

import cn.metaq.common.core.dto.Pagination;
import cn.metaq.data.jpa.BaseBiz;
import cn.metaq.data.jpa.BaseTemplate;
import cn.metaq.sqlbuilder.biz.ModelTaskBiz;
import cn.metaq.sqlbuilder.dao.ModelTaskExtDao;
import cn.metaq.sqlbuilder.dao.TaskDao;
import cn.metaq.sqlbuilder.model.dto.ModelTaskDTO;
import cn.metaq.sqlbuilder.model.entity.ModelTaskExt;
import cn.metaq.sqlbuilder.model.entity.QModelTaskExt;
import cn.metaq.sqlbuilder.model.entity.QTask;
import cn.metaq.sqlbuilder.model.entity.Task;
import cn.metaq.sqlbuilder.model.qo.ModelTaskQo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 模型任务业务逻辑接口
 *
 * @author zantang
 */
@Service
public class ModelTaskBizImpl extends BaseBiz<ModelTaskExt, ModelTaskExt, Long, ModelTaskExtDao> implements
    ModelTaskBiz {

  /** task */
  @Resource
  private TaskDao taskDao;

  @Resource
  private BaseTemplate baseTemplate;
  @Autowired
  private JPAQueryFactory jqf;

  @Override
  public Pagination<ModelTaskDTO> list(ModelTaskQo modelTaskQo, int offset, int limit) {

    QTask task = QTask.task;
    QModelTaskExt modelTaskExt = QModelTaskExt.modelTaskExt;

    List<ModelTaskDTO> list = jqf.select(
            Projections.fields(ModelTaskDTO.class, task.id, task.name, task.type, task.mode, modelTaskExt.build,
                modelTaskExt.improve, modelTaskExt.columns, modelTaskExt.mid, task.createdBy, task.createdTs,
                task.updatedBy, task.updatedTs))
        .from(task)
        .leftJoin(modelTaskExt)
        .on(task.id.eq(modelTaskExt.id))
        .where(task.type.eq(0))
        .where(this.buildQuery(modelTaskQo,task,modelTaskExt).toArray(com.querydsl.core.types.Predicate[]::new))
        .fetch();

    Pagination pagination = new Pagination();
    pagination.setOffset(offset);
    pagination.setLimit(limit);
    pagination.setTotal(Math.toIntExact(this.count(modelTaskQo)));
    pagination.setData(list);
    return pagination;
  }

  private List<com.querydsl.core.types.Predicate> buildQuery(ModelTaskQo queryDTO,QTask task,QModelTaskExt modelTaskExt){

    List<com.querydsl.core.types.Predicate> predicates = new ArrayList<>();

    if (queryDTO.getMid() != null) {
      predicates.add(modelTaskExt.mid.eq(queryDTO.getMid()));
    }
    if (queryDTO.getMode() != null) {
      predicates.add(task.mode.eq(queryDTO.getMode()));
    }
    if (queryDTO.getName() != null && queryDTO.getName().length() > 0) {
      predicates.add(task.name.like(queryDTO.getName()));
    }
    return predicates;
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

    QTask task = QTask.task;
    QModelTaskExt modelTaskExt = QModelTaskExt.modelTaskExt;

    return jqf.selectOne()
        .from(task)
        .leftJoin(modelTaskExt)
        .on(task.id.eq(modelTaskExt.id))
        .where(task.type.eq(0))
        .where(this.buildQuery(queryDTO,task,modelTaskExt).toArray(com.querydsl.core.types.Predicate[]::new))
        .fetchCount();
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
